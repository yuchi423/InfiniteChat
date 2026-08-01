package com.yuchi.realtimeservice.websocket;

import com.yuchi.common.constant.CommonConstant;
import com.yuchi.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.micrometer.common.util.StringUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

@RequiredArgsConstructor
public class WebSocketAuthHeader extends ChannelInboundHandlerAdapter {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)  {
        if (msg instanceof FullHttpRequest request){
            String authHeader = request.headers().get("Authorization");//client在握手时提供Token，服务器借此判断用户身份
            if (authHeader == null || authHeader.isEmpty()) {
                ctx.close();
                return;
            }
            try {
                Claims claims = JwtUtil.parse(authHeader); //检查token是否合法
                if (claims == null) { //检查是否过期
                    ctx.close();
                    return;
                }

                String userId = claims.getSubject();
                if (userId == null || userId.isEmpty()) { //从Token中得到userid
                    ctx.close();
                    return;
                }

                String storedToken = stringRedisTemplate.opsForValue().get(CommonConstant.ACCESS_TOKEN_PREFIX + userId);
                if (StringUtils.isEmpty(storedToken) || !authHeader.equals(storedToken)) {
                    //Redis保存的是服务器当前认可的登录Token，JWT验证Token为真，Redis验证Token仍被承认
                    ctx.close();
                    return;
                }

                // 3. 绑定用户与 channel
                ChannelManager.addUserChannel(userId, ctx.channel());
                ChannelManager.addChannelUser(userId, ctx.channel());
                ctx.fireChannelRead(msg);
            } catch (Exception e) {
                // 记录日志
                ctx.close();
            }

        } else {
            ctx.fireChannelRead(msg);
        }
    }
}