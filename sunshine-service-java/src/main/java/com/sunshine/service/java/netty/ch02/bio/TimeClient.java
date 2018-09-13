package com.sunshine.service.java.netty.ch02.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static com.sunshine.service.java.netty.utils.CloseUtils.close;
import static com.sunshine.service.java.netty.utils.Constant.*;

/**
 * @Description:
 * @Date: 2018/9/12 08:52
 * @Auther: yangzhaoxu
 */
public class TimeClient {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            //初始化
            socket = new Socket(LOCAL_IP, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            //发送命令
            out.println(QUERY_TIME_ORDER);
            System.out.println("[Client][向服务器发送的命令为:" + QUERY_TIME_ORDER + "]");

            //读取结果
            String res = in.readLine();
            System.out.println("[Client][从服务器时间获取的时间为:" + res + "]");

        } catch (Exception e) {
            close(in, out, socket);
        } finally {
            close(in, out, socket);
        }
    }
}
