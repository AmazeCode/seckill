package com.seckill.service.impl;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
import com.seckill.dto.ExposerDto;
import com.seckill.dto.SeckillExecution;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.model.Seckill;
import com.seckill.model.SuccessKilled;
import com.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

//@Component代表所有的组件 @Service @Dao @Controller
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //注入service依赖，J2EE的注解方式@Resource，@Inject
    @Autowired//spring依赖
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    /**
     * MD5盐值字符串，用于混淆MD5
     */
    private final String salt = "fajldstynJIBF*@$#^*^*$124";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public ExposerDto exportSeckillUrl(long seckillId) {
        //获取特定秒杀记录
        Seckill seckill = seckillDao.queryById(seckillId);

        if(seckill == null){
            return new ExposerDto(false, seckillId);
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new ExposerDto(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        //转化特定字符串的过程,不可逆
        String md5 = getMD5(seckillId);
        return new ExposerDto(true, md5, seckillId);
    }

    /**
     * MD5加密
     * @param seckillId
     * @return
     */
    private String getMD5(long seckillId){
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 使用注解控制方法的有点：
     * 1：开发团队达成一致约定，明确标注事务方法的编程风格。
     * 2：保证事务方法的执行时间尽可能短，不要穿插其他的网络操作，（操作缓存
     * /HTTP请求）RPC/HTTP请求，如果还需要操作那就剥离到事务方法外，方式是做一个更加上层的方法
     * 3、不是所有的方法都需要事务，如只有一条修改操作，或者是只读操作控制，当有两条以上的修改操作或者是只读操作中有select或update时需要事务
     */
    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        try {
            //执行秒杀逻辑:减库存 + 记录购买行为；
            Date newTime = new Date();
            int updateCount = seckillDao.reduaceNumber(seckillId, newTime);
            if (updateCount <= 0) {
                //没有更新到记录，秒杀结束
                throw new SeckillCloseException("seckill is close");
            } else {
                //记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    //重复秒杀
                    throw new RepeatKillException("seckill is repeat");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw  e1;
        } catch (RepeatKillException e2) {
            throw  e2;
        } catch (SeckillException e) {
            logger.error(e.getMessage(),e);
            //所有编译期异常,转化为运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }
}
