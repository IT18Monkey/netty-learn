package com.github.it18monkey.nio2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AsyncServerSocket {
    public static void main(final String[] args) throws IOException {
        CountDownLatch countDownLatch=new CountDownLatch(1);
        AsynchronousServerSocketChannel serverSocketChannel= AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8080));
        Attachment attachment = new Attachment(serverSocketChannel);
        serverSocketChannel.accept(attachment, new CompletionHandler<AsynchronousSocketChannel, Attachment>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Attachment attachment) {
                try{
                    attachment.getServerSocketChannel().accept(attachment, this);//继续接受别的连接
                    SocketAddress clientAddr=client.getRemoteAddress();
                    System.out.println("收到的新连接："+clientAddr);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    client.read(byteBuffer, byteBuffer, new ReadCompletionHandler(client));
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Attachment attachment) {
                        exc.printStackTrace();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel client;

    public ReadCompletionHandler(AsynchronousSocketChannel client) {
        this.client = client;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String request = new String(body, "UTF-8");
            System.out.println("server receive :" + request);
            dowrite("server response,hello world");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void dowrite(String response) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(response.getBytes());
        client.write(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                //如果还没有发送完继续发送
                if (byteBuffer.hasRemaining()) {
                    client.write(byteBuffer,byteBuffer,this);
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        exc.printStackTrace();
    }
}
