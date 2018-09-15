package com.sunshine.service.java.netty.ch04.sticklossOK;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import static com.sunshine.service.java.netty.utils.CloseUtils.close;
import static com.sunshine.service.java.netty.utils.Constant.PORT;

/**
 * @Description: 没有粘包问题的服务端
 * @Date: 2018/9/15 10:56
 * @Auther: yangzhaoxu
 */
public class Server {


    public ServerBootstrap initServerBootstrap(EventLoopGroup parentGroup, EventLoopGroup childGroup) {
        ServerBootstrap b = new ServerBootstrap();

        b.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {

                        /**
                         * 解决了tcp粘包和拆包的情况
                         */
                        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));//按行切换的文本解码器
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new StickylossOKServerHandler());
                    }
                });
        return b;

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
