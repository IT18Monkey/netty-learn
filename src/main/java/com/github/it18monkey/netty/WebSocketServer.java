package com.github.it18monkey.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.Date;

/**
 * Created by wanghaohao on 2018/8/28.
 */
public class WebSocketServer {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("http-codec", new HttpServerCodec())//将请求或应答消息编解码为http消息
                                    .addLast("aggregator", new HttpObjectAggregator(65536))//将http的多个部分组合成一条完整消息
                                    .addLast("http-chunked", new ChunkedWriteHandler())
                                    .addLast("webSocketHandler", new WebSocketServerProtocolHandler("/ws"))
                                    .addLast("handler", new WebSocketServerHandler());
                        }
                    });
            Channel c = b.bind(port).sync().channel();

            System.out.println("web socket server started at port " + port);

            c.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

class WebSocketServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TextWebSocketFrame message = (TextWebSocketFrame) msg;
        String content = message.text();
        System.out.println("server recieved:" + content);
        ctx.writeAndFlush(new TextWebSocketFrame("欢迎使用netty websocket服务，当前时间：" + new Date().toString()));
    }
}
