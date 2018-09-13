package com.sunshine.service.java.netty.ch02.aio;

import java.io.IOException;
import java.nio.channels.AsynchronousServerSocketChannel;

/**
 * @Description:
 * @Date: 2018/9/13 09:34
 * @Auther: yangzhaoxu
 */
public class AIOServerHandler implements Runnable {
    private int port;

    private  AsynchronousServerSocketChannel asynServerSocketChannel;

    public AIOServerHandler(int port){
        this.port = port;
        try {
            asynServerSocketChannel = AsynchronousServerSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void run() {

    }
}
