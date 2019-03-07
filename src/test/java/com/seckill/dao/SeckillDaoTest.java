package com.seckill.dao;

import com.seckill.model.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 配置Spring和Junit整合,为了Junit启动时，加载springIoc容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void queryById() throws Exception{

        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception{
        //Java没有保存形参的记录 query(int offset,int limit)->queryAll(arg0,arg1)
        //解决方式，采用这种形式：@Param("offset") int offset,@Param("limit") int limit
        List<Seckill> seckills = seckillDao.queryAll(0,100);
        for (Seckill seckill : seckills){
            System.out.println(seckill);
        }

    }

    @Test
    public void reduaceNumber() throws Exception{

        Date killtime = new Date();
        int updateCount = seckillDao.reduaceNumber(1000L,killtime);
        System.out.println("updateCount="+updateCount);
    }




}