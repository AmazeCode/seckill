package com.seckill.enums;

/**
 * 秒杀枚举
 */
public enum SeckillStateEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROT(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改");

    /**
     * 秒杀状态
     */
    private int state;

    /**
     * 秒杀状态描述
     */
    private String stateInfo;

    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    /**
     * 获取状态
     * @param indexOf
     * @return
     */
    public static SeckillStateEnum stateOf(int indexOf){

        for (SeckillStateEnum state : values()){//TODO测试
            if(state.getState() == indexOf){
                return state;
            }
        }
        return null;
    }
}
