package com.sunshine.service.java.javathread;

/**
 * @Description:
 * @Date: 2019/5/8 10:22
 * @Auther: yangzhaoxu
 */
public class WaitNotifyTest {

    public static Object obj = new Object();

    public static void main(String[] args) {
        ThreadWait t1 = new ThreadWait();
        ThreadNotify t2 = new ThreadNotify();

        // t1执行后进入Waiting状态
        t1.start();

        doSleep(1000L);

        // t2执行后将t1唤醒
        t2.start();
    }


    static class ThreadWait extends Thread {
        @Override
        public void run() {
            synchronized (obj) {
                try {
                    //t1调用wait()后，会进入WAITING状态
                    obj.wait();
                } catch (InterruptedException e) {
                }

                // t1被t2唤醒后，等t2释放锁后再抢obj锁，如果抢到则进入RUNABLE状态，执行以下代码；如果没有抢到,那么进入BLOCKED状态等待获取obj锁
                System.out.println("线程" + Thread.currentThread().getName() + "获取到了obj监控锁");
            }
            System.out.println("线程" + Thread.currentThread().getName() + "释放了obj监控锁");
        }
    }


    static class ThreadNotify extends Thread {
        @Override
        public void run() {
            synchronized (obj) {

                // t2调用notify()后，会唤醒t1,但只有等t2退出synchronized块释放obj锁后，t1才去抢obj锁
                obj.notify();


                System.out.println("线程" + Thread.currentThread().getName() + "调用了object.notify(),但还没有释放obj监控锁");
                doSleep(2000L);
                System.out.println("线程" + Thread.currentThread().getName() + "调用了object.notify(),准备释放obj监控锁");
            }

            // t2已经退出synchronized块释放obj锁，处于唤醒状态的t1才去抢obj锁
            doSleep(5000L);
            System.out.println("线程" + Thread.currentThread().getName() + "释放了obj监控锁");
        }
    }


    public static void doSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }

    }


}
