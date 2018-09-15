package com.sunshine.service.java.netty.ch04.sticklossOK;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import static com.sunshine.service.java.netty.ch04.sticklossOK.StickylossOKClientHandler.lineSepatator;
import static com.sunshine.service.java.netty.utils.ProcessUtils.process;

/**
 * @Description:
 * @Date: 2018/9/14 09:48
 * @Auther: yangzhaoxu
 */
public class StickylossOKServerHandler extends ChannelHandlerAdapter {


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
        String currTime = process(body, (++recCounter)) + lineSepatator;
        ByteBuf resp = Unpooled.copiedBuffer(currTime.getBytes());

        // 注意:字节数组(内存)-->缓存区(内存)：避免频繁唤醒Selector来发送消息,所以只写入到缓存区
        ctx.writeAndFlush(resp);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
