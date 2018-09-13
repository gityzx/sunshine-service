package com.sunshine.service.java.netty.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

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




}
