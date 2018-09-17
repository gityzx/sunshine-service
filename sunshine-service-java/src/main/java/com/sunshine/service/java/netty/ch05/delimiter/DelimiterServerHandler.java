package com.sunshine.service.java.netty.ch05.delimiter;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Description:
 * @Date: 2018/9/14 09:48
 * @Auther: yangzhaoxu
 */
public class DelimiterServerHandler extends ChannelHandlerAdapter {
    private int recCounter;

    /**
     * 收到客户端发来的请求后调用此方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        /**
         * 这里直接取出字符串
         */
        String body = (String) msg;
        // 处理命令的业务逻辑
        System.out.println("[Server][服务器接受到的命令为:" + body + "]--[当前计数器为:" + (++recCounter) + "]");

        // 注意:字节数组(内存)-->缓存区(内存)：避免频繁唤醒Selector来发送消息,所以只写入到缓存区
        ctx.writeAndFlush(Unpooled.copiedBuffer((body + "$_").getBytes()));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
