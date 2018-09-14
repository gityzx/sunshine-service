package com.sunshine.service.java.netty.ch04.stickyloss;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import static com.sunshine.service.java.netty.utils.CloseUtils.close;
import static com.sunshine.service.java.netty.utils.Constant.PORT;

/**
 * @Description:
 * @Date: 2018/9/14 07:46
 * @Auther: yangzhaoxu
 */
public class TimeServer {

    public ServerBootstrap initServerBootstrap(EventLoopGroup parentGroup, EventLoopGroup childGroup) {
        ServerBootstrap b = new ServerBootstrap();

        b.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StickylossServerHandler());
                    }
                });
        return b;

    }


    public static void main(String[] args) {

        // 创建Reactor线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // 配置ServerBootstrap
        ServerBootstrap b = new TimeServer().initServerBootstrap(bossGroup, workerGroup);

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