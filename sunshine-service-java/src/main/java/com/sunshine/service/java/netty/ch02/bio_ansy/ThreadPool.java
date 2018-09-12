package com.sunshine.service.java.netty.ch02.bio_ansy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Date: 2018/9/12 09:20
 * @Auther: yangzhaoxu
 */
public class ThreadPool {
    private ExecutorService executorService;

    public ThreadPool(int maxPoolSize, int queueSize) {
        this.executorService = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                maxPoolSize,
                120L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task) {
        this.executorService.execute(task);
    }
}
