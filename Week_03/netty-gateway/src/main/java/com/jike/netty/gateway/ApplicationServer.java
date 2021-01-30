package com.jike.netty.gateway;

import com.jike.netty.gateway.server.NettyNioServer;

import java.net.InetSocketAddress;

/**
 * @author: taoxp
 * @create: 2021-01-30 15:15
 */

public class ApplicationServer {
    public static Integer PORT=8080;
    public static String urls="http://localhost:8088,http://localhost:8802";

    public static void main(String[] args) {
        NettyNioServer server=new NettyNioServer();
        InetSocketAddress inetSocketAddress=new InetSocketAddress(PORT);
        System.out.println("netty server begin staring");
        System.out.println("netty server port : "+PORT);
        server.start(inetSocketAddress);
        System.out.println("netty server stop");
    }

}
