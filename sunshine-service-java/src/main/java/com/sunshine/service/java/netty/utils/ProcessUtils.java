package com.sunshine.service.java.netty.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

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
        sender.write(msgBuffer);
        if (!msgBuffer.hasRemaining()) {
            System.out.println("发送消息完毕!");
        }

    }


}
