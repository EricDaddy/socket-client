package com.xkk.spring.client.datagram;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Scanner;

/**
 * 功能描述：
 *
 * @Author: XKK
 * @Date: 2020/11/3 11:23
 */
@Service
public class DatagramTest {

    public void send() throws IOException {
        //获取DatagramChannel数据报通道
        DatagramChannel datagramChannel = DatagramChannel.open();
        //设置通道为非阻塞
        datagramChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(20);
        Scanner scanner = new Scanner(System.in);
        System.out.println("UDP客户端启动成功");
        System.out.println("请输入发送内容");
        while (scanner.hasNext()) {
            String next = scanner.next();
            //写入数据据到缓冲区
            buffer.put(next.getBytes());
            buffer.flip();
            //把buffer发送到数据报通道
            datagramChannel.send(buffer,new InetSocketAddress("127.0.0.1",8091));
            buffer.clear();
        }
        datagramChannel.close();
    }

    public static void main(String[] args) throws IOException {
        DatagramTest datagramTest =new DatagramTest();
        datagramTest.send();
    }
}
