package com.seckill.utils;

import java.util.concurrent.CountDownLatch;

/**
 * 线程计数器
 */
public class CountDownLaunchThreadTest {

    /**
     * 子线程计数器，记录运行的线程数
     */
    private static CountDownLatch countDownLatch;

    public static void main(String[] args) {

        try{
            for (int i = 0; i < 10; i++) {
                try {
                    CountDownLaunchThread thread = new CountDownLaunchThread(countDownLatch);
                    thread.start();//启动线程
                    //zip保存目录 格式：/文件夹/日期/8位编码
                } catch (Exception grab) {
                    continue;
                }
            }
            countDownLatch.await();//等待线程执行结束
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
