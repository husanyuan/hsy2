package com.netty.fifthexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class MyServer {
    public static void main(String[] args) throws Exception {
        // 开启两个事件循环组，事件循环组会自动构建EventLoop，服务器一般开启两个，提高效率
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // Netty的引导类，用于简化开发
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 把事件循环组加入引导程序，开启socket，加入业务控制器，这里是加入一个初始化类，其中包含了很多业务控制器，
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)).childHandler(new WebSocketChannelInitializer());
            // 服务器绑定端口监听
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            // 监听服务器关闭监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
