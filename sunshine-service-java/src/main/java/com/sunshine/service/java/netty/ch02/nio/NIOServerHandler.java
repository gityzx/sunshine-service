package com.sunshine.service.java.netty.ch02.nio;

import com.sunshine.service.java.netty.utils.CloseUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static com.sunshine.service.java.netty.utils.ProcessUtils.doSend;
import static com.sunshine.service.java.netty.utils.ProcessUtils.process;

/**
 * @Description:
 * @Date: 2018/9/12 09:42
 * @Auther: yangzhaoxu
 */
public class NIOServerHandler implements Runnable {
    private Selector selector;
    private ServerSocketChannel server;
    private volatile boolean stop;

    public NIOServerHandler(int port) {
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

        /**
         * 循环监听channel
         */
        while (!stop) {

            try {
                this.selector.select(1000);

                /**
                 * 监听channel
                 */
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();

                    /**
                     * key的核心处理器
                     */
                    try {
                        this.processKey(key);
                    } catch (Exception e) {
                        CloseUtils.close(key);
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 如果停止监听,那么关闭资源
         */
        CloseUtils.close(this.selector);
    }


    private void processKey(SelectionKey key) {
        try {
            if (key.isValid()) {


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
                 * SelectionKey.OP_READ: 处理新接入的请求消息
                 *
                 *  网络管道-->缓存区(内存)-->字节数组(内存)-->反序列化成Object(内存)
                 */
                if (key.isReadable()) {

                    // 网络管道-->缓存区(内存)
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int readBytes = sc.read(readBuffer);


                    if (readBytes > 0) {
                        // 缓存区(内存)-->字节数组(内存)
                        readBuffer.flip();
                        byte[] bytes = new byte[readBuffer.remaining()];
                        readBuffer.get(bytes);

                        // 字节数组(内存)-->反序列化成Object(内存)
                        String body = new String(bytes, "UTF-8");

                        // 处理命令的业务逻辑
                        String currTime = process(body);

                        // 返回结果
                        doSend(sc, currTime);


                    } else if (readBytes < 0) {
                        //链路关闭
                        CloseUtils.close(key);
                    } else {
                        //读取0字节,所以忽略
                    }

                }
                /**
                 * SelectionKey.OP_CONNECT
                 */
                if (key.isConnectable()) {

                }
                /**
                 * SelectionKey.OP_WRITE:
                 */
                if (key.isWritable()) {

                }

            }
        } catch (Exception e) {
            CloseUtils.close(key);
        }

    }


    private void close() {
        this.stop = true;
    }
}
