package com.netty.firstexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sbs = new ServerBootstrap();
            sbs.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());
            ChannelFuture channelFuture = sbs.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅的关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
