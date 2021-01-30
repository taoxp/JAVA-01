package com.jike.netty.gateway.inboundhandler;

import com.jike.netty.gateway.ApplicationServer;
import com.jike.netty.gateway.filter.HttpRequestFilter;
import com.jike.netty.gateway.filter.impl.HeaderRequestFilter;
import com.jike.netty.gateway.outboundhandler.OkHttpOutBoundHandler;
import com.jike.netty.gateway.router.DefaultRouterStrategy;
import com.jike.netty.gateway.router.RouterStrategy;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Arrays;

/**
 * @author: taoxp
 * @create: 2021-01-30 16:02
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    private OkHttpOutBoundHandler okHttpOutBoundHandler;
    private RouterStrategy routerStrategy;
    private HttpRequestFilter filter;

    public HttpInboundHandler() {
        this.okHttpOutBoundHandler = new OkHttpOutBoundHandler();
        this.routerStrategy = new DefaultRouterStrategy(Arrays.asList(ApplicationServer.urls.split(",")));
        this.filter = new HeaderRequestFilter();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request= (FullHttpRequest) msg;
        okHttpOutBoundHandler.requestHandler(request, ctx, filter, routerStrategy);
    }
}
