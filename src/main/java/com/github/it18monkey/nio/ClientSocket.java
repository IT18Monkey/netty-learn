package com.github.it18monkey.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by wanghaohao on 2018/8/10.
 */
public class ClientSocket {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8080));

        System.out.println("请输入发送给服务端的数据:");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String data = scanner.nextLine();
            // 发送请求
            ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
            socketChannel.write(buffer);
            System.out.println("发送的数据：" + data);

//            // 读取响应
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(readBuffer);
            String result = new String(readBuffer.array(), "UTF-8");
            System.out.println("返回值: " + result);
        }
    }
}
