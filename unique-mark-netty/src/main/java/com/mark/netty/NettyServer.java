package com.mark.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/7 15:15
 * @QQ: 85104982
 */
public class NettyServer {
    public void start(int port) throws InterruptedException {
        EventLoopGroup boos= new NioEventLoopGroup();
        EventLoopGroup worker=new NioEventLoopGroup();

        try {
            ServerBootstrap b=new ServerBootstrap();
            b.group(boos,worker).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new HttpRequestDecoder());
                    socketChannel.pipeline().addLast(new HttpResponseEncoder());
                    socketChannel.pipeline().addLast(new IdServiceHandler());
                }
            })
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture future=b.bind(port).sync();
            future.channel().closeFuture().sync();
        }finally {
            boos.shutdownGracefully();
            worker.shutdownGracefully();
        }



    }

    public static void main(String[] args) {
        try {
            new NettyServer().start(8080);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
