package com.jike.netty.gateway.server;

import com.jike.netty.gateway.inboundhandler.HttpInboundInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author: taoxp
 * @create: 2021-01-30 15:17
 */
public class NettyNioServer {
    private final Integer BOSS_THREAD_COUNT=1;
    private final Integer WORKER_THREAD_COUNT=10;
    public void start(InetSocketAddress inetSocketAddress){
        System.out.println("init boss event group");
        EventLoopGroup boss=new NioEventLoopGroup(BOSS_THREAD_COUNT);
        System.out.println("init worker event group");
        EventLoopGroup worker=new NioEventLoopGroup(WORKER_THREAD_COUNT);

        try {
            System.out.println("init ServerBootstrap");
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class).localAddress(inetSocketAddress);
            //注入channelHandler
            serverBootstrap.childHandler(new HttpInboundInitializer());
            ChannelFuture future=serverBootstrap.bind().sync();
            future.channel().closeFuture().sync();
        }catch (InterruptedException e){
            System.out.println("netty server start failed!");
            System.out.println(e.getMessage());
        }finally {
            boss.shutdownGracefully();
            boss.shutdownGracefully();
        }

    }
}
