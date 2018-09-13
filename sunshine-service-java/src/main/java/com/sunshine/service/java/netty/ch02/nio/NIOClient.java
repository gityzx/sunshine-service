package com.sunshine.service.java.netty.ch02.nio;

import static com.sunshine.service.java.netty.utils.Constant.LOCAL_IP;
import static com.sunshine.service.java.netty.utils.Constant.PORT;

/**
 * @Description:
 * @Date: 2018/9/13 08:24
 * @Auther: yangzhaoxu
 */
public class NIOClient {
    public static void main(String[] args) {
        new Thread(new NIOClientHandler(LOCAL_IP, PORT)).start();
    }
}
