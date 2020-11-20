package com.xkk.spring.client.reactor;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * 功能描述：
 *
 * @Author: XKK
 * @Date: 2020/11/4 17:00
 */
@Service
public class ReactorTest {

    public void connectServer() throws IOException {
        //开启socketChannel通道
        SocketChannel socketChannel = SocketChannel.open();
        //开启与服务器的连接
        socketChannel.socket().connect(new InetSocketAddress("127.0.0.1",8091));
        //设置为非阻塞
        socketChannel.configureBlocking(false);
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入发送内容:");
        while(scanner.hasNext()) {
            String message = scanner.next();
//        String message = "你好！";
            ByteBuffer buffer = ByteBuffer.allocate(200);
            buffer.put(message.getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
        socketChannel.close();
    }

    public static void main(String[] args) throws IOException{
        ReactorTest reactorTest = new ReactorTest();
        reactorTest.connectServer();
    }
}
