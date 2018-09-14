package com.sunshine.service.java.netty.ch03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import static com.sunshine.service.java.netty.utils.ProcessUtils.process;

/**
 * @Description:
 * @Date: 2018/9/14 08:09
 * @Auther: yangzhaoxu
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 网络管道-->缓存区(内存)
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];

        // 缓存区(内存)-->字节数组(内存)
        buf.readBytes(req);

        // 字节数组(内存)-->反序列化成Object(内存)
        String body = new String(req, "UTF-8");

        // 处理命令的业务逻辑
        String currTime = process(body);
        ByteBuf resp = Unpooled.copiedBuffer(currTime.getBytes());

        // 注意:字节数组(内存)-->缓存区(内存)：避免频繁唤醒Selector来发送消息,所以只写入到缓存区
        ctx.write(resp);
    }


    /**
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 注意:缓存区(内存)-->网络管道
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
