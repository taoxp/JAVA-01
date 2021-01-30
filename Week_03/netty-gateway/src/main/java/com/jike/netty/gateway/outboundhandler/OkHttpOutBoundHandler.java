package com.jike.netty.gateway.outboundhandler;

import com.jike.netty.gateway.filter.HttpRequestFilter;
import com.jike.netty.gateway.router.RouterStrategy;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author: taoxp
 * @create: 2021-01-23 00:20
 */
public class OkHttpOutBoundHandler {
    public  Response requestGet(final FullHttpRequest inbound,String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder();

        request.url(url)
                .header("gateway",inbound.headers().get("gateway"))
                .get();
        Call call = okHttpClient.newCall(request.build());
        String result=null;
        try {
            System.out.println("begin request url:"+url);
            Response response = call.execute();
            if (!response.isSuccessful()){
                System.out.println("request:"+url+" failed. error code:"+response.code());
                return null;
            }
            return response;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void responseHandler(FullHttpRequest request,final Response response, ChannelHandlerContext context){
        FullHttpResponse fullHttpResponse=null;
        try {
            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(response.body().string().getBytes()));
            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().setInt("Content-Length", Integer.parseInt(response.header("Content-Length")));

        }catch (Exception e){
            e.printStackTrace();
            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(context, e);
        }finally {
                if (!HttpUtil.isKeepAlive(request)) {
                    context.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    context.write(fullHttpResponse);
                }
            context.flush();
        }

    }

    public void requestHandler(FullHttpRequest request, ChannelHandlerContext context, HttpRequestFilter requestFilter, RouterStrategy routerStrategy){
        String url=routerStrategy.router()+request.uri();
        requestFilter.filter(request, context);
        responseHandler(request,requestGet(request,url),context);

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
