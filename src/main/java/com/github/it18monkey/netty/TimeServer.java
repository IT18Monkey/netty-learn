package com.github.it18monkey.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by wanghaohao on 2018/8/22.
 */
public class TimeServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new LineBasedFrameDecoder(1024))
                                    .addLast(new StringDecoder())
                                    .addLast(new TimeServerHandler());
                        }
                    });
            ChannelFuture f = bootstrap.bind(8080).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

 class TimeServerHandler extends ChannelInboundHandlerAdapter {
     @Override
     public void channelActive(ChannelHandlerContext ctx) throws Exception {
         System.out.println("client connnect:"+ctx.channel().remoteAddress());
     }
     @Override
     public void channelReadComplete(ChannelHandlerContext ctx) {
         ctx.flush();
     }
     @Override
     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
         System.out.println("server receive: "+msg);
         String resp="hello client"+System.lineSeparator();
         ByteBuf byteBuf= Unpooled.copiedBuffer(resp.getBytes());
         ctx.writeAndFlush(byteBuf);
     }
     @Override
     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
         // Close the connection when an exception is raised.
         cause.printStackTrace();
         ctx.close();
     }
 }
