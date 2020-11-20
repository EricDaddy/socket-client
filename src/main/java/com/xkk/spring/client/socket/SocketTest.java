package com.xkk.spring.client.socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 功能描述：
 *
 * @Author: XKK
 * @Date: 2020/11/2 9:47
 */
public class SocketTest {
    //设置文件的编码格式
    private static final Charset charset = StandardCharsets.UTF_8;

    public void sendFile() throws IOException{
        String fileName = "phhhhh.txt";

        try {
            File srcFile = new File("C:\\Users\\86156\\Desktop\\srcFile.txt");
            if(!srcFile.exists()) {
                return;
            }
            FileChannel fileChannel = new FileInputStream(srcFile).getChannel();
            //创建Socket通道
            SocketChannel socketChannel = SocketChannel.open();
            //开启与服务端的连接
            socketChannel.socket().connect(new InetSocketAddress("127.0.0.1",8091));
            //设置为非阻塞模式
            socketChannel.configureBlocking(false);
            while (!socketChannel.finishConnect()) {
                //
            }
            //发送文件名称
            ByteBuffer fileNameByteBuffer = charset.encode(fileName);
            socketChannel.write(fileNameByteBuffer);
            //发送文件长度
            ByteBuffer fileLongBuffer = ByteBuffer.allocate(200);
            fileLongBuffer.putLong(srcFile.length());

            //翻转成为读模式
            fileLongBuffer.flip();
            socketChannel.write(fileLongBuffer);
            //清空重置缓冲区
            fileLongBuffer.clear();

            int length = 0;
            int progress = 0;
            //将文件通道数据写入缓冲区
            while((length = fileChannel.read(fileLongBuffer)) > 0) {
                fileLongBuffer.flip();
                socketChannel.write(fileLongBuffer);
                fileLongBuffer.clear();
                //记录写入的长度
                progress += length;
                System.out.println(" | " + (100*progress / srcFile.length()) + "% |");
            }
            //文件写入缓冲区失败
            if(length == -1) {
                fileChannel.close();
                socketChannel.shutdownOutput();
                socketChannel.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        SocketTest socketTest = new SocketTest();
        socketTest.sendFile();
    }
}
