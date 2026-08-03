package com.yuchi.realtimeservice.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.NettyRuntime;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/*
负责说明：服务器怎么启动、监听哪个端口、使用多少线程、
每条连接安装哪些处理器
主要做了：创建一台Netty WebSocket服务器-->规定每条客户端连接的数据应该经过哪些处理步骤
 */
@Configuration
@RequiredArgsConstructor
public class NettyService {

    private final int port = 9101;

    private final NioEventLoopGroup bossGroup = new NioEventLoopGroup(1); //创建只有一个线程的EventLoopGroup，负责监听时间、接收连接并交由WorkerGroup
    private final NioEventLoopGroup workerGroup = new NioEventLoopGroup(NettyRuntime.availableProcessors() * 2);
    private final StringRedisTemplate stringRedisTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;


    @PostConstruct
    public void start() throws InterruptedException {
        //调用时自动启动Netty服务器
        run();
    }

    public void run() throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap(); //Netty服务器的装配器、配置器和启动器
        //规定使用哪些EventLoopGroup, 哪种SeverChannel，每个客户端连接装什么Pipeline，监听哪个端口
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline channelPipeline = socketChannel.pipeline(); //获取当前这个客户端SocketChannel所拥有的Pipeline
                        channelPipeline.addLast(new IdleStateHandler(60,0,0));
                        channelPipeline.addLast(new HttpServerCodec());
                        channelPipeline.addLast(new HttpObjectAggregator(65536));
                        channelPipeline.addLast(new WebSocketAuthHeader(stringRedisTemplate));
                        channelPipeline.addLast(new WebSocketServerProtocolHandler("/ws/netty"));
                        channelPipeline.addLast(new WebSocketHandler(kafkaTemplate));
                    }
                });
        serverBootstrap.bind(port).sync(); //将服务器绑定到本机9101端口
    }

    @PreDestroy
    public void destroy() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
