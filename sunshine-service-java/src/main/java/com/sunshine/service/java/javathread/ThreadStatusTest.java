package com.sunshine.service.java.javathread;

/**
 * @Description:
 * @Date: 2019/5/7 15:05
 * @Auther: yangzhaoxu
 */
public class ThreadStatusTest {

    public static void main(String[] args) {

        // new status
        newStatus();

        // terminated status
        terminatedStatus();

    }

    /**
     * 功能描述: 对于NEW来说，一旦离开，就永远回不来了；
     * 当一个线程创建后，也就是new了一个Thread，那么这个Thread的state就是NEW
     * 有且只有这种情况下，才为NEW，不会从任何状态转换而来
     * 也就是说如果一个线程状态已经不再是NEW，那么他永远不可能再重新回到NEW的状态，这是一个起点
     */
    public static void newStatus() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world");
            }
        });

        System.out.println(t.isAlive());
        System.out.println(t.getState());

    }


    /**
     * 功能描述: 对于TERMINATED来说，一旦到达， 就永远回不去了；
     * 当一个线程终止后，就进入状态TERMINATED
     * TERMINATED作为线程的终点，一旦进入TERMINATED状态，将不再能够转换为其他状态
     */
    public static void terminatedStatus() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world");
            }
        });

        t.start();


        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t.isAlive());
        System.out.println(t.getState());


    }
}
