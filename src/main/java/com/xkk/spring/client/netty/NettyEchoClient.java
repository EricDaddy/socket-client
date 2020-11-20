package com.xkk.spring.client.netty;

import cn.hutool.core.date.DateUnit;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * 功能描述：
 *
 * @Author: XKK
 * @Date: 2020/11/20 10:04
 */
public class NettyEchoClient {

    private int serverPort;
    private String serverIp;
    Bootstrap bootstrap = new Bootstrap();

    public NettyEchoClient(String ip,int port) {
        this.serverIp = ip;
        this.serverPort = port;
    }

    public void runClient() {

        //创建线程组
        EventLoopGroup work = new NioEventLoopGroup();

        try {
            bootstrap.group(work);
            bootstrap.channel(NioSocketChannel.class);
            //设置远程服务器ip，端口
            bootstrap.remoteAddress(serverIp,serverPort);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            //装配通道流水线
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new EchoClientHandler());
                }
            });

            ChannelFuture connectFuture = bootstrap.connect();
            //连接监听器
            connectFuture.addListener((ChannelFuture cf) -> {
                if(cf.isSuccess()) {
                    System.out.println("客户端连接成功");
                } else {
                    System.out.println("客户端连接失败");
                }
            });
            //阻塞直到连接成功
            connectFuture.sync();
            //获取连接通道，进行数据传输
            Channel channel = connectFuture.channel();
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入发送内容:");
            while(scanner.hasNext()) {
                String next = scanner.next();
                //将输入转化成字节数组
                byte[] bytes = next.getBytes("UTF-8");
                //发送缓冲区
                ByteBuf buf = channel.alloc().buffer();
                buf.writeBytes(bytes);
                channel.writeAndFlush(buf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            work.shutdownGracefully();
        }

    }


    public static void main(String[] args) {
        NettyEchoClient nettyEchoClient = new NettyEchoClient("127.0.0.1",8091);
        nettyEchoClient.runClient();
    }
}
