package com.sunshine.service.java.netty.ch05.fixedlength;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import static com.sunshine.service.java.netty.utils.CloseUtils.close;
import static com.sunshine.service.java.netty.utils.Constant.PORT;

/**
 * @Description: 没有粘包问题的服务端
 * @Date: 2018/9/15 10:56
 * @Auther: yangzhaoxu
 */
public class Server {


    public ServerBootstrap initServerBootstrap(EventLoopGroup parentGroup, EventLoopGroup childGroup) {
        ServerBootstrap server = new ServerBootstrap();

        server.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new LoggingHandler(LogLevel.INFO))//添加了日志的打印
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {

                        /**
                         * 定长解码器 解决了tcp粘包和拆包的情况
                         */
                        ch.pipeline()
                                .addLast(new FixedLengthFrameDecoder(3))
                                .addLast(new StringDecoder())
                                .addLast(new FixedLengthServerHandler());
                    }
                });
        return server;

    }


    public static void main(String[] args) {

        // 创建Reactor线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // 配置ServerBootstrap
        ServerBootstrap b = new Server().initServerBootstrap(bossGroup, workerGroup);

        try {

            // (同步)绑定端口等待成功
            ChannelFuture f = b.bind(PORT).sync();

            // (同步)等待服务端监听端口关闭
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            // 优雅退出
            close(bossGroup, workerGroup);
        } finally {
            // 优雅退出
            close(bossGroup, workerGroup);
        }
    }
}
