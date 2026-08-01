package com.yuchi.realtimeservice.websocket;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;
/*
服务器拥有Channel，而业务代码需要userid，需要一个中间层manager将网络和业务概念连接
 */
public class ChannelManager {
    //推送消息，通过userId找到Channel后就可以想用户发送WebSocket消息
    private static final ConcurrentHashMap<String, Channel> USER_CHANNEL_MAP = new ConcurrentHashMap<String, Channel>();

    private static final ConcurrentHashMap<Channel, String> CHANNEL_USER_MAP = new ConcurrentHashMap<Channel, String>();

    public static void addUserChannel(String userId, Channel channel) {
        USER_CHANNEL_MAP.put(userId, channel);
    }

    public static void addChannelUser(String userId, Channel channel) {
        CHANNEL_USER_MAP.put(channel, userId);
    }

    public static void removeChannelUser(Channel channel) {
        CHANNEL_USER_MAP.remove(channel);
    }

    public static void removeUserChannel(String userId) {
        USER_CHANNEL_MAP.remove(userId);
    }

    public static Channel getChannelByUserId(String userId) {
        return USER_CHANNEL_MAP.get(userId);
    }

    public static String getUserIdByChannel(Channel channel) {
        return CHANNEL_USER_MAP.get(channel);
    }

}