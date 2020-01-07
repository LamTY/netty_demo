package com.qf.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;

public class NettyServer {
    public static void main(String[] args) {


        //创建主从线程池
        EventLoopGroup master = new NioEventLoopGroup();
        EventLoopGroup salver = new NioEventLoopGroup();

        //创建服务器的初始化引导对象
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //配置引导对象
        serverBootstrap.group(master, salver)
                .channel(NioSctpServerChannel.class)
                .childHandler(new NettyChannelHander());
        ChannelFuture future = serverBootstrap.bind(8080);
        try {
            future.sync();
            System.out.println("端口绑定完成，服务已经启动！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
