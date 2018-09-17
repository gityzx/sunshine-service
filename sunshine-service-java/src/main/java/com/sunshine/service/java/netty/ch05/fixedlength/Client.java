package com.sunshine.service.java.netty.ch05.fixedlength;

import com.sunshine.service.java.netty.utils.Constant;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import static com.sunshine.service.java.netty.utils.CloseUtils.close;

/**
 * @Description: 没有粘包问题的客户端
 * @Date: 2018/9/15 10:56
 * @Auther: yangzhaoxu
 */
public class Client {

    public Bootstrap initBootstrap(EventLoopGroup reactor) {
        // 客户端辅助启动类
        Bootstrap client = new Bootstrap();
        client
                .group(reactor)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new LoggingHandler(LogLevel.INFO))//添加了日志的打印
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {

                        /**
                         * 按行切换的文本解码器解决了tcp粘包和拆包的情况
                         */
                        ch.pipeline()
                                .addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("$_".getBytes())))
                                .addLast(new StringDecoder())
                                .addLast(new FixedLengthClientHandler());
                    }
                });

        return client;
    }


    public static void main(String[] args) {
        // 创建Reactor
        EventLoopGroup reactor = new NioEventLoopGroup();

        // 配置Bootstrap
        Bootstrap b = new Client().initBootstrap(reactor);

        try {
            //开始连接
            ChannelFuture f = b.connect(Constant.LOCAL_IP, Constant.PORT).sync();
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            // 优雅退出
            close(reactor);
        } finally {
            // 优雅退出
            close(reactor);
        }
    }
}
