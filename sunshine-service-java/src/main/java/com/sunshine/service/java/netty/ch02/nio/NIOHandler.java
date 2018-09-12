package com.sunshine.service.java.netty.ch02.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static com.sunshine.service.java.netty.Constant.close;

/**
 * @Description:
 * @Date: 2018/9/12 09:42
 * @Auther: yangzhaoxu
 */
public class NIOHandler implements Runnable {
    private Selector selector;
    private ServerSocketChannel server;
    private volatile boolean stop;

    public NIOHandler(int port) {
        try {
            // 初始化selector和serverChannel
            this.selector = Selector.open();
            this.server = ServerSocketChannel.open();
            this.server.configureBlocking(false);
            this.server.socket().bind(new InetSocketAddress(port), 1024);

            //将serverChannel注册到seletor
            this.server.register(this.selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                this.selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();

                    this.processKey(key);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }


    private void processKey(SelectionKey key) {
        try {
            /**
             * SelectionKey.OP_ACCEPT: 处理新接入的请求消息
             */
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);//注册读取
            }

            /**
             * SelectionKey.OP_CONNECT
             */
            if (key.isConnectable()) {

            }
            // SelectionKey.OP_READ: 处理新接入的请求消息
            if (key.isReadable()) {

            }
            // SelectionKey.OP_WRITE: 处理新接入的请求消息
            if (key.isWritable()) {

            }

            if (key.isValid()) {

            }
        } catch (Exception e) {
            close(key);
        }

    }
}
