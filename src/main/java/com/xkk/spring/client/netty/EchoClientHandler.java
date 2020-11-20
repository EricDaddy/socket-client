package com.xkk.spring.client.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.management.BufferPoolMXBean;

/**
 * 功能描述：
 *
 * @Author: XKK
 * @Date: 2020/11/20 10:43
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    //读取通道数据包
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        int len = byteBuf.readableBytes();
        byte[] bytes = new byte[len];
        byteBuf.getBytes(0,bytes);

        System.out.println("客户端接收到写回的消息：" + new String(bytes,"UTF-8"));

        //手动释放缓冲区
        byteBuf.release();
    }
}
