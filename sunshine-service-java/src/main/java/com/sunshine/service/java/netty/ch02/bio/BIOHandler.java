package com.sunshine.service.java.netty.ch02.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static com.sunshine.service.java.netty.utils.CloseUtils.close;
import static com.sunshine.service.java.netty.utils.ProcessUtils.process;

/**
 * @Description:
 * @Date: 2018/9/12 08:06
 * @Auther: yangzhaoxu
 */
public class BIOHandler implements Runnable {
    private Socket socket;

    public BIOHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            //初始化
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);

            String currTime = null;
            String body = null;
            while (true) {
                body = in.readLine();
                if (body == null) {
                    break;
                }

                //判断body内容同时返回结果
                out.println(process(body));
            }
        } catch (IOException e) {
            //异常后关闭资源
            close(in, out, this.socket);
        }
    }


}
