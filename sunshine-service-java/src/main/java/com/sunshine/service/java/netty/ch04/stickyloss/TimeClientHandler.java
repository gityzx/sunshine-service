package com.sunshine.service.java.netty.ch04.stickyloss;

import com.sunshine.service.java.netty.utils.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Description:
 * @Date: 2018/9/14 08:43
 * @Auther: yangzhaoxu
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
    private ByteBuf firstMessage = null;

    public TimeClientHandler() {
        byte[] req = Constant.QUERY_TIME_ORDER.getBytes();
        this.firstMessage = Unpooled.buffer(req.length);
        this.firstMessage.writeBytes(req);
    }


    /**
     * 当客户端和服务端TCP链路建立成功后,Netty NIO 线程会调用channelActive方法来发送命令
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(this.firstMessage);
    }

    /**
     * 当服务器返回应答消息时,会调用channelRead方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 网络管道-->缓存区(内存)
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];

        // 缓存区(内存)-->字节数组(内存)
        buf.readBytes(req);

        // 字节数组(内存)-->反序列化成Object(内存)
        String body = new String(req, "UTF-8");

        System.out.println("当前时间是:" + body);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
