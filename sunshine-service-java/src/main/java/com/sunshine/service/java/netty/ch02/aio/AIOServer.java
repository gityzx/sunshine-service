package com.sunshine.service.java.netty.ch02.aio;

import static com.sunshine.service.java.netty.utils.Constant.PORT;

/**
 * @Description:
 * @Date: 2018/9/13 21:19
 * @Auther: yangzhaoxu
 */
public class AIOServer {
    public static void main(String[] args) {
        new Thread(new AIOServerHandler(PORT)).start();
    }
}
