package com.sunshine.service.java.netty.ch02.aio;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import static com.sunshine.service.java.netty.utils.CloseUtils.close;
import static com.sunshine.service.java.netty.utils.ProcessUtils.doSend;
import static com.sunshine.service.java.netty.utils.ProcessUtils.process;

/**
 * @Description:
 * @Date: 2018/9/13 19:49
 * @Auther: yangzhaoxu
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        if (this.channel == null) {
            this.channel = channel;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        try {

            attachment.flip();
            byte[] body = new byte[attachment.remaining()];
            attachment.get(body);
            String req = new String(body, "UTF-8");
            String currTime = process(req);

            // 异步发送
            doSend(this.channel, currTime);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        close(this.channel);

    }


}
