package com.sunshine.service.java.netty.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.SocketChannel;

import static com.sunshine.service.java.netty.utils.CloseUtils.close;
import static com.sunshine.service.java.netty.utils.Constant.BAD_ORDER;
import static com.sunshine.service.java.netty.utils.Constant.QUERY_TIME_ORDER;

/**
 * @Description:
 * @Date: 2018/9/13 08:48
 * @Auther: yangzhaoxu
 */
public class ProcessUtils {

    public static String process(String reqBody) {
        System.out.println("[Server][服务器接受到的命令为:" + reqBody + "]");
        return (QUERY_TIME_ORDER.equalsIgnoreCase(reqBody)) ? (String.valueOf(System.currentTimeMillis())) : (BAD_ORDER);
    }


    /**
     * Object(内存)-->序列化成字节数组(内存)-->缓存区(内存)-->灌入到网络管道中
     *
     * @param sender
     * @param msg
     * @throws IOException
     */
    public static void doSend(SocketChannel sender, String msg) throws IOException {

        // Object(内存)-->序列化成字节数组(内存)
        byte[] msgBytes = msg.getBytes();

        // 字节数组(内存)-->缓存区(内存)
        ByteBuffer msgBuffer = ByteBuffer.allocate(msgBytes.length);
        msgBuffer.put(msgBytes);

        // 缓存区(内存)-->灌入到网络管道中
        msgBuffer.flip();

        //发送
        sender.write(msgBuffer);
        if (!msgBuffer.hasRemaining()) {
            System.out.println("发送消息完毕!");
        }

    }


    /**
     * AIO发送: Object(内存)-->序列化成字节数组(内存)-->缓存区(内存)-->灌入到网络管道中
     *
     * @param sender
     * @param msg
     */
    public static void doSend(AsynchronousSocketChannel sender, String msg) {
        // Object(内存)-->序列化成字节数组(内存)
        byte[] msgBytes = msg.getBytes();

        // 字节数组(内存)-->缓存区(内存)
        ByteBuffer msgBuffer = ByteBuffer.allocate(msgBytes.length);
        msgBuffer.put(msgBytes);

        // 缓存区(内存)-->灌入到网络管道中
        msgBuffer.flip();

        //异步发送
        sender.write(
                msgBuffer,
                msgBuffer,
                new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer buffer) {
                        // 迭代异步发送
                        if (buffer.hasRemaining()) {
                            sender.write(buffer, buffer, this);
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer buffer) {
                        close(sender);
                    }
                });
    }


}
