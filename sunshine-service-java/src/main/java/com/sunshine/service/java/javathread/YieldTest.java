package com.sunshine.service.java.javathread;

/**
 * @Description: 主线程main中启动了两个线程t1和t2。
 * t1和t2在run()会引用同一个对象的同步锁，即synchronized(obj)。
 * 在t1运行过程中，虽然它会调用Thread.yield()；但是，t2是不会获取cpu执行权的。因为，t1并没有释放“obj所持有的同步锁”
 * @Date: 2019/5/7 09:58
 * @Auther: yangzhaoxu
 */
public class YieldTest {
    private static Object obj = new Object();

    public static void main(String[] args) {
        ThreadA t1 = new ThreadA("t1");
        ThreadA t2 = new ThreadA("t2");
        t1.start();
        t2.start();
    }

    static class ThreadA extends Thread {
        public ThreadA(String name) {
            super(name);
        }

        @Override
        public void run() {
            // 获取obj对象的同步锁
            synchronized (obj) {
                for (int i = 0; i < 10; i++) {
                    System.out.printf("%s [%d]:%d\n", this.getName(), this.getPriority(), i);
                    // i整除4时，调用yield
                    if (i % 4 == 0) {
                        Thread.yield();
                    }
                }
            }
        }
    }
}
