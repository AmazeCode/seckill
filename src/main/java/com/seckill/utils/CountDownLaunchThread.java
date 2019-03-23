package com.seckill.utils;

import java.util.concurrent.CountDownLatch;

/**
 * 线程池Demo
 */
public class CountDownLaunchThread extends Thread{

    /**
     * 子线程计数器，记录运行的线程数
     */
    private CountDownLatch countDownLatch;

    public CountDownLaunchThread(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void run(){
        System.out.println("测试");
    }
}
