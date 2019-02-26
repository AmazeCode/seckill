package com.seckill.dao;

import com.seckill.model.SuccessKilled;

public interface SuccessKilledDao {

    /**
     * 插入购买明细，可过滤重复（表中联合唯一主键可以过滤重复）
     * @param seckillId
     * @param userPhone
     * @return
     * 插入的行数
     */
    int insertSuccessKilled(long seckillId, long userPhone);

    /**
     * 查询SuccessKilled并且携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(long seckillId);
}
