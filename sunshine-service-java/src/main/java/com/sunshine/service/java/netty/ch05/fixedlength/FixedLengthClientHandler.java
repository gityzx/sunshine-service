package com.sunshine.service.java.netty.ch05.fixedlength;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Description:
 * @Date: 2018/9/15 15:03
 * @Auther: yangzhaoxu
 */
public class FixedLengthClientHandler extends ChannelHandlerAdapter {

    /**
     * 当客户端和服务端TCP链路建立成功后,Netty NIO 线程会调用channelActive方法来发送命令
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 循环100次缓存-->网络
        ctx.writeAndFlush(Unpooled.copiedBuffer(("123456$_").getBytes()));
    }

    /**
     * 当服务器返回应答消息时,会调用channelRead方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器返回的内容是:" + msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
