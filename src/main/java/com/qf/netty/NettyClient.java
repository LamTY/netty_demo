package com.qf.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;
import java.util.Scanner;

public class NettyClient {
    public static void main(String[] args) {
        //创建引导对象
        Bootstrap bootstrap = new Bootstrap();
        //设置线程模型
        bootstrap.group(new NioEventLoopGroup()).channel(NioSocketChannel.class).handler(new SimpleChannelInboundHandler<ByteBuf>() {
            protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                System.out.println("接收到服务器信息" + byteBuf.toString(Charset.forName("utf-8")));
            }
        });

        //连接服务器
        ChannelFuture future = bootstrap.connect("127.0.0.1", 8080);

        try {
            future.sync();
            System.out.println("连接服务器成功");

            Scanner scanner = new Scanner(System.in);

            while (true){
                System.out.println("请输入发送内容");
                String content = scanner.next();
                Channel channel = future.channel();
                byte[] bytes = content.getBytes();
                ByteBuf buf = Unpooled.buffer(bytes.length);
                buf.writeBytes(bytes);
                channel.writeAndFlush(buf);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
