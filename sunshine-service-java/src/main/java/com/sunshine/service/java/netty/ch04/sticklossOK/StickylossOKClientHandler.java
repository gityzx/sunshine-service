package com.sunshine.service.java.netty.ch04.sticklossOK;

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
public class StickylossOKClientHandler extends ChannelHandlerAdapter {
    public static String lineSepatator = System.getProperty("line.separator");
    private int ackCount = 0;

    public StickylossOKClientHandler() {
        System.out.println("line.separator:" + lineSepatator);
    }


    /**
     * 当客户端和服务端TCP链路建立成功后,Netty NIO 线程会调用channelActive方法来发送命令
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //将数据序列化成字节数组
        byte[] req = (Constant.QUERY_TIME_ORDER + lineSepatator).getBytes();

        // 循环100次
        for (int i = 0; i < 100; i++) {
            //字节数组-->缓存
            ByteBuf buffer = Unpooled.buffer(req.length);
            buffer.writeBytes(req);

            // 缓存-->网络
            ctx.writeAndFlush(buffer);
        }
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
        /**
         * 这里直接取出字符串
         */
        String body = (String) msg;
        // 理论上来说ackCount也是100才对
        System.out.println("当前时间是:" + body + ",客户端收到ACK的数量为:" + (++ackCount));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
