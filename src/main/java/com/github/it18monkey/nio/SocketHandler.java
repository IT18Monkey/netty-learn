package com.github.it18monkey.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by wanghaohao on 2018/8/10.
 */
public class SocketHandler implements Runnable {

    private SocketChannel socketChannel;

    public SocketHandler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public void run() {
        ByteBuffer readBuffer = ByteBuffer.allocate(8);
        try {
            int num ;
            while ((num=socketChannel.read(readBuffer))>0){
                readBuffer.flip();
                byte[] bytes = new byte[num];
                readBuffer.get(bytes);

                System.out.println(Thread.currentThread().getName()+" 收到数据：" + new String(bytes, "UTF-8"));
                ByteBuffer writeBuffer = ByteBuffer.wrap("服务端返回的数据...".getBytes());
                socketChannel.write(writeBuffer);
                readBuffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}