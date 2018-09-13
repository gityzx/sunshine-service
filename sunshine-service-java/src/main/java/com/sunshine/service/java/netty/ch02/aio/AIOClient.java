package com.sunshine.service.java.netty.ch02.aio;

import static com.sunshine.service.java.netty.utils.Constant.LOCAL_IP;
import static com.sunshine.service.java.netty.utils.Constant.PORT;

/**
 * @Description:
 * @Date: 2018/9/13 21:18
 * @Auther: yangzhaoxu
 */
public class AIOClient {
    public static void main(String[] args) {
        new Thread(new AIOClientHandler(LOCAL_IP, PORT)).start();
    }
}
