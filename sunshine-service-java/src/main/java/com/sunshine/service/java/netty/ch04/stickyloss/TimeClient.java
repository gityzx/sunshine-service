package com.sunshine.service.java.netty.ch04.stickyloss;

import com.sunshine.service.java.netty.utils.Constant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import static com.sunshine.service.java.netty.utils.CloseUtils.close;

/**
 * @Description: 有粘包问题的客户端
 * @Date: 2018/9/14 08:43
 * @Auther: yangzhaoxu
 */
public class TimeClient {

    public Bootstrap initBootstrap(EventLoopGroup reactor) {
        // 客户端辅助启动类
        Bootstrap b = new Bootstrap();
        b
                .group(reactor)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {

                        /**
                         * 有tcp粘包和拆包的情况
                         */
                        ch.pipeline().addLast(new StickylossClientHandler());
                    }
                });

        return b;
    }


    public static void main(String[] args) {
        // 创建Reactor
        EventLoopGroup reactor = new NioEventLoopGroup();

        // 配置Bootstrap
        Bootstrap b = new TimeClient().initBootstrap(reactor);

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
