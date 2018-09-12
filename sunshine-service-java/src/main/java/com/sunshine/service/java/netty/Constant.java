package com.sunshine.service.java.netty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;

/**
 * @Description:
 * @Date: 2018/9/12 08:12
 * @Auther: yangzhaoxu
 */
public class Constant {

    public static final String QUERY_TIME_ORDER = "QUERY TIME ORDER";

    public static final String BAD_ORDER = "BAD ORDER";

    public static final int PORT = 8080;
    public static final String LOCAL_IP = "127.0.0.1";


    public static String process(String reqBody) {
        System.out.println("[Server][服务器接受到的命令为:" + reqBody + "]");
        return (QUERY_TIME_ORDER.equalsIgnoreCase(reqBody)) ? (String.valueOf(System.currentTimeMillis())) : (BAD_ORDER);
    }


    public static void close(BufferedReader in) {
        if (in != null) {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public static void close(BufferedReader in,
                             PrintWriter out, Socket socket) {
        close(in);
        close(out);
        close(socket);

    }


    public static void close(PrintWriter out) {
        if (out != null) {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void close(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void close(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                System.out.println("The Time Server Close!");
                serverSocket.close();
                serverSocket = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(SelectionKey key) {
        if (key != null) {
            key.cancel();
            if (key.channel() != null) {
                try {
                    key.channel().close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }


}
