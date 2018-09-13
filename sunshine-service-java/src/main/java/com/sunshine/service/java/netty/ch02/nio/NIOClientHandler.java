package com.sunshine.service.java.netty.ch02.nio;

import com.sunshine.service.java.netty.utils.CloseUtils;
import com.sunshine.service.java.netty.utils.Constant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static com.sunshine.service.java.netty.utils.ProcessUtils.doSend;

/**
 * @Description:
 * @Date: 2018/9/13 08:26
 * @Auther: yangzhaoxu
 */
public class NIOClientHandler implements Runnable {
    private Selector selector;
    private SocketChannel socketChannel;

    private String host;
    private int port;
    private volatile boolean stop;


    public NIOClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            this.selector = Selector.open();
            this.socketChannel = SocketChannel.open();
            this.socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            this.doConnect();
        } catch (IOException e) {
            e.printStackTrace();
        }


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
                 * SelectionKey.OP_CONNECT
                 */
                if (key.isConnectable()) {
                    SocketChannel sc = (SocketChannel) key.channel();

                    //这里仅仅是连接成功，但是还没有进行读写操作，所以需要再次注册异步读写操作
                    if (sc.finishConnect()) {
                        sc.register(this.selector, SelectionKey.OP_READ);
                        doSend(sc, Constant.QUERY_TIME_ORDER);
                    } else {
                        System.exit(1);
                    }

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

                        System.out.println("接收到的时间为:" + body);

                        this.close();


                    } else if (readBytes < 0) {
                        //链路关闭
                        CloseUtils.close(key);
                    } else {
                        //读取0字节,所以忽略
                    }
                }

                /**
                 * SelectionKey.OP_WRITE:
                 */
                if (key.isWritable()) {

                }
                /**
                 * SelectionKey.OP_ACCEPT: 处理新接入的请求消息
                 */
                if (key.isAcceptable()) {

                }
            }
        } catch (Exception e) {
            CloseUtils.close(key);
        }

    }

    private void doConnect() throws IOException {

        // 连接成功也是一种注册
        if (this.socketChannel.connect(new InetSocketAddress(this.host, this.port))) {
            socketChannel.register(this.selector, SelectionKey.OP_READ);
            doSend(this.socketChannel, Constant.QUERY_TIME_ORDER);
        } else {
            // 没有连接成功那么再注册连接
            this.socketChannel.register(this.selector, SelectionKey.OP_CONNECT);
        }
    }


    private void close() {
        this.stop = true;
    }


}
