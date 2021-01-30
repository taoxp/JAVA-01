package com.jike.netty.gateway.inboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author: taoxp
 * @create: 2021-01-30 17:34
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline cp=ch.pipeline();
        cp.addLast(new HttpServerCodec());
        //p.addLast(new HttpServerExpectContinueHandler());
        cp.addLast(new HttpObjectAggregator(1024 * 1024));
        cp.addLast(new HttpInboundHandler());
    }
}
