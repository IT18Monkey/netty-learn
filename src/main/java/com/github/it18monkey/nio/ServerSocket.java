package com.github.it18monkey.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wanghaohao on 2018/8/10.
 */
public class ServerSocket {

    public static void main(String[] args) throws Exception {

        // 监听 8080 端口进来的 TCP 链接

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int readyChannels = selector.select();
            if (readyChannels == 0) {
                continue;
            }
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(8);
                    try {
                        int num;
                        String request = "";
                        while ((num = socketChannel.read(readBuffer)) > 0) {
                            readBuffer.flip();
                            byte[] bytes = new byte[num];
                            readBuffer.get(bytes);
                            request += new String(bytes, "UTF-8");
                            readBuffer.clear();
                        }
                        System.out.println(Thread.currentThread().getName() + " 收到数据：" + request);
                        ByteBuffer writeBuffer = ByteBuffer.wrap("服务端返回的数据...".getBytes("UTF-8"));
                        socketChannel.write(writeBuffer);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
