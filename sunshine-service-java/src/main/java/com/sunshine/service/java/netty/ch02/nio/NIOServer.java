package com.sunshine.service.java.netty.ch02.nio;

import static com.sunshine.service.java.netty.utils.Constant.PORT;

/**
 * @Description:
 * @Date: 2018/9/13 08:21
 * @Auther: yangzhaoxu
 */
public class NIOServer {
    public static void main(String[] args) {
        new Thread(new NIOServerHandler(PORT)).start();
    }
}
