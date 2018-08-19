package com.github.it18monkey.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AsyncServerSocket {
    public static void main(String[] args) throws IOException {
        AsynchronousServerSocketChannel serverSocketChannel= AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8080));
        Attachment attachment=new Attachment();
        serverSocketChannel.accept(attachment, new CompletionHandler<AsynchronousSocketChannel, Attachment>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Attachment attachment) {
                try{
                    SocketAddress clientAddr=client.getRemoteAddress();
                    System.out.println("收到的新连接："+clientAddr);




                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Attachment attachment) {

            }
        });
    }
}
