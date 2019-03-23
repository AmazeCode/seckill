package com.seckill.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    private static ExecutorService pool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws InterruptedException {
        for (int i= 0; i< 100; i++) {
            threadPoolDemo(i);
        }
        pool.shutdown();
        pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }

    private static void threadPoolDemo(int i) {
        pool.submit(new Runnable() {
            @Override
            public void run() {}
        });
    }

}
