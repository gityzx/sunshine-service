package com.sunshine.service.java.netty.ch02.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.sunshine.service.java.netty.utils.CloseUtils.close;
import static com.sunshine.service.java.netty.utils.Constant.PORT;

/**
 * @Description:
 * @Date: 2018/9/12 08:42
 * @Auther: yangzhaoxu
 */
public class TimeServer {
    public static void main(String[] args) {
        ServerSocket acceptor = null;
        try {
            acceptor = new ServerSocket(PORT);
            Socket socket = null;
            while (true) {
                socket = acceptor.accept();
                new Thread(new BIOHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            close(acceptor);
        } finally {
            close(acceptor);
        }
    }
}
