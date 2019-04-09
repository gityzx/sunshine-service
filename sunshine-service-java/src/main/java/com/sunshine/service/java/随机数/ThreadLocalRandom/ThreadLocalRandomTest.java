package com.sunshine.service.java.随机数.ThreadLocalRandom;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description:
 * @Date: 2019/3/28 10:57
 * @Auther: yangzhaoxu
 */
public class ThreadLocalRandomTest {

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName());
            System.out.println(ThreadLocalRandom.current().nextInt(100));
        }
    }
}
