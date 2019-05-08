package com.sunshine.service.java.javathread;

/**
 * @Description: 主线程main中启动了两个线程t1和t2。
 * t1和t2在run()会引用同一个对象的同步锁，即synchronized(obj)。
 * 在t1运行过程中，虽然它会调用Thread.sleep(100)；但是，t2是不会获取cpu执行权的。因为，t1并没有释放“obj所持有的同步锁”！
 * 注意，若我们注释掉synchronized (obj)后再次执行该程序，t1和t2是可以相互切换的。
 * @Date: 2019/5/7 09:58
 * @Auther: yangzhaoxu
 */
public class JoinTest {
    private static Object obj = new Object();

    public static void main(String[] args) {
        ThreadA t1 = new ThreadA("t1");
        ThreadA t2 = new ThreadA("t2");
        t1.start();
        t2.start();
    }


    /**
     * 功能描述: ThreadA 通过 synchronized (obj)代码块来获取锁
     */
    static class ThreadA extends Thread {
        public ThreadA(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.printf("%s: %d\n", this.getName(), i);
                    // i能被4整除时，休眠100毫秒
                    if (i % 4 == 0) {
                        Thread.currentThread().join(2000);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 功能描述: ThreadB没有通过 synchronized (obj)代码块来获取锁
     */
    static class ThreadB extends Thread {
        public ThreadB(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.printf("%s: %d\n", this.getName(), i);
                    // i能被4整除时，休眠100毫秒
                    if (i % 4 == 0) {
                        Thread.sleep(5000);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
