package com.sunshine.service.java.netty.utils;

import io.netty.channel.EventLoopGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * @Description:
 * @Date: 2018/9/13 08:46
 * @Auther: yangzhaoxu
 */
public class CloseUtils {

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
                             PrintWriter out,
                             Socket socket) {
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


    public static void close(AsynchronousSocketChannel channel) {
        if (channel != null) {
            try {
                System.out.println("The Time Server Close!");
                channel.close();
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

    public static void close(Selector selector) {
        //优雅关闭:selector关闭后，所有注册在上面的Channel和Pipe都会自动去注册并关闭，所以不需要重复释放资源
        if (selector != null) {
            try {
                selector.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public static void close(EventLoopGroup... groups) {
        //优雅关闭释放资源
        for (EventLoopGroup g : groups) {
            if (g != null) {
                try {
                    g.shutdownGracefully();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

}
