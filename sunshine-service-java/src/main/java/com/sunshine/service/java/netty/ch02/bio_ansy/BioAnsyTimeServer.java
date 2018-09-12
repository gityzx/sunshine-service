package com.sunshine.service.java.netty.ch02.bio_ansy;

import com.sunshine.service.java.netty.ch02.bio.BIOHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.sunshine.service.java.netty.Constant.PORT;
import static com.sunshine.service.java.netty.Constant.close;

/**
 * @Description:
 * @Date: 2018/9/12 08:42
 * @Auther: yangzhaoxu
 */
public class BioAnsyTimeServer {
    public static void main(String[] args) {

        //初始化线程池
        ThreadPool pool = new ThreadPool(50, 10000);

        ServerSocket acceptor = null;
        try {
            acceptor = new ServerSocket(PORT);
            Socket socket = null;
            while (true) {
                socket = acceptor.accept();

                /**
                 * 使用线程池来代替之前的单线程
                 * old:  new Thread(new TimeServerHandler(socket)).start();
                 */
                pool.execute(new BIOHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
            close(acceptor);
        } finally {
            close(acceptor);
        }
    }
}
