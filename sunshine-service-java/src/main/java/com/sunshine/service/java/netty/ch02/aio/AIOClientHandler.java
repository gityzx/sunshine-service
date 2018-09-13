package com.sunshine.service.java.netty.ch02.aio;

import com.sunshine.service.java.netty.utils.Constant;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

import static com.sunshine.service.java.netty.utils.CloseUtils.close;

/**
 * @Description:
 * @Date: 2018/9/13 20:45
 * @Auther: yangzhaoxu
 */
public class AIOClientHandler implements Runnable {

    private String host;
    private int port;
    private AsynchronousSocketChannel channel;
    private CountDownLatch latch;


    public AIOClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            this.channel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        this.latch = new CountDownLatch(1);
        InetSocketAddress address = new InetSocketAddress(this.host, this.port);

        /**
         * 异步请求连接
         */
        this.channel.connect(
                address,
                this,
                new CompletionHandler<Void, AIOClientHandler>() {

                    /**
                     * 如果请求成功
                     * @param result
                     * @param attachment
                     */
                    @Override
                    public void completed(Void result, AIOClientHandler attachment) {

                        // Object(内存)-->序列化成字节数组(内存)
                        byte[] msgBytes = Constant.QUERY_TIME_ORDER.getBytes();

                        // 字节数组(内存)-->缓存区(内存)
                        ByteBuffer msgBuffer = ByteBuffer.allocate(msgBytes.length);
                        msgBuffer.put(msgBytes);

                        // 缓存区(内存)-->灌入到网络管道中
                        msgBuffer.flip();

                        //异步发送
                        channel.write(msgBuffer, msgBuffer, new CompletionHandler<Integer, ByteBuffer>() {

                            @Override
                            public void completed(Integer result, ByteBuffer attachment) {

                                if (attachment.hasRemaining()) {
                                    /**
                                     * 递归发送
                                     */
                                    channel.write(attachment, attachment, this);
                                } else {
                                    /**
                                     * 异步读取返回结果
                                     */
                                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                                    channel.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                                        @Override
                                        public void completed(Integer result, ByteBuffer readBuffer) {
                                            readBuffer.flip();
                                            byte[] readBytes = new byte[readBuffer.remaining()];
                                            readBuffer.put(readBytes);
                                            try {
                                                String res = new String(readBytes, "UTF-8");
                                                System.out.println("当前时间为:" + res);

                                                latch.countDown();
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }


                                        }

                                        @Override
                                        public void failed(Throwable exc, ByteBuffer readBuffer) {
                                            close(channel);
                                            latch.countDown();

                                        }
                                    });


                                }

                            }

                            @Override
                            public void failed(Throwable exc, ByteBuffer attachment) {
                                close(channel);
                                latch.countDown();
                            }
                        });

                    }

                    @Override
                    public void failed(Throwable exc, AIOClientHandler attachment) {
                        close(channel);
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
