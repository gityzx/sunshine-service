package com.sunshine.service.java.netty.ch02.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @Description:
 * @Date: 2018/9/13 09:34
 * @Auther: yangzhaoxu
 */
public class AIOServerHandler implements Runnable {
    int port;
    AsynchronousServerSocketChannel asynServerSocketChannel;
    CountDownLatch latch;

    public AIOServerHandler(int port) {
        this.port = port;
        try {
            this.asynServerSocketChannel = AsynchronousServerSocketChannel.open();

            // 服务端监听端口
            this.asynServerSocketChannel.bind(new InetSocketAddress(port));

            System.out.println("服务器绑定的端口为:" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void run() {

        this.latch = new CountDownLatch(1);
        /**
         * 异步监听
         */
        this.asynServerSocketChannel.accept(
                this,
                new CompletionHandler<AsynchronousSocketChannel, AIOServerHandler>() {
                    /**
                     * 回调成功
                     * @param channel
                     * @param attachment
                     */
                    @Override
                    public void completed(AsynchronousSocketChannel channel, AIOServerHandler attachment) {
                        // 迭代异步监听
                        attachment.asynServerSocketChannel.accept(attachment, this);

                        // 异步读取
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer, buffer, new ReadCompletionHandler(channel));
                    }

                    /**
                     * 回调失败
                     * @param exc
                     * @param attachment
                     */
                    @Override
                    public void failed(Throwable exc, AIOServerHandler attachment) {
                        exc.printStackTrace();
                        latch.countDown();
                    }
                });


        try {
            this.latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
