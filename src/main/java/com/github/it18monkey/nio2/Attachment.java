package com.github.it18monkey.nio2;

import java.nio.channels.AsynchronousServerSocketChannel;

public class Attachment {
    private AsynchronousServerSocketChannel serverSocketChannel;

    public Attachment(AsynchronousServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    public AsynchronousServerSocketChannel getServerSocketChannel() {
        return serverSocketChannel;
    }

    public void setServerSocketChannel(AsynchronousServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }
}
