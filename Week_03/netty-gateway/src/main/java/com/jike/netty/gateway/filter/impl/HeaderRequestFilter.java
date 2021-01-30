package com.jike.netty.gateway.filter.impl;

import com.jike.netty.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author: taoxp
 * @create: 2021-01-30 16:36
 */
public class HeaderRequestFilter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullHttpRequest, ChannelHandlerContext context) {
        fullHttpRequest.headers().set("gateway","netty-gateway");
    }
}
