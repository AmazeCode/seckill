package com.seckill.dto;

import com.seckill.enums.SeckillStateEnum;
import com.seckill.model.SuccessKilled;

/**
 * 封装秒杀执行后结果
 */
public class SeckillExecutionDto {

    /**
     * 秒杀id
     */
    private long seckillId;

    /**
     * 秒杀执行结果状态
     */
    private int state;

    /**
     * 状态表示
     */
    private String stateInfo;

    /**
     * 秒杀成功对象
     */
    private SuccessKilled successKilled;

    /**
     * 构造方法
     * @param seckillId
     * @param seckillStateEnum
     * @param successKilled
     */
    public SeckillExecutionDto(long seckillId, SeckillStateEnum seckillStateEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = seckillStateEnum.getState();
        this.stateInfo = seckillStateEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    /**
     * 构造方法
     * @param seckillId
     * @param seckillStateEnum
     */
    public SeckillExecutionDto(long seckillId, SeckillStateEnum seckillStateEnum) {
        this.seckillId = seckillId;
        this.state = seckillStateEnum.getState();
    }

    /**
     * 构造方法
     * @param seckillId
     * @param state
     * @param stateInfo
     */
    public SeckillExecutionDto(long seckillId, int state, String stateInfo) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
