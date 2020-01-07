package com.qf.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
public class NettyChannelHander extends SimpleChannelInboundHandler<ByteBuf>{

    final List<Channel> channels = new ArrayList<Channel>();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有一个客户端连接了");
        channels.add(ctx.channel());

    }

    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {

        System.out.println("接收到了客户端的消息：" + byteBuf.toString(Charset.forName("utf-8")));

        for (Channel channel : channels) {
            if(channel != ctx.channel()){
                ByteBuf buf = Unpooled.buffer(byteBuf.capacity());
                buf.writeBytes(byteBuf);
                channel.writeAndFlush(buf);
            }
        }
    }
}
