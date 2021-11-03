package cn.orzlinux.step4_file_img.server;

import cn.orzlinux.step4_file_img.bean.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.HashMap;
import java.util.Map;

/**
 * 对应用户服务端会话管理
 */
public class SocketSession {
    public static final AttributeKey<SocketSession> SESSION_KEY = AttributeKey.valueOf("SESSION_KEY");
    private Channel channel;
    private User user;

    public String getUserCookieId() {
        return userCookieId;
    }

    public void setUserCookieId(String userCookieId) {
        this.userCookieId = userCookieId;
    }

    private String userCookieId;
    private String group;
    // session中存储的属性值
    private Map<String,Object> map = new HashMap<>();

    public SocketSession(Channel channel) {
        this.channel = channel;
        // channel和session绑定
        channel.attr(SocketSession.SESSION_KEY).set(this);
    }

    // 返回ctx对应的SocketSession，ctx.channel->找session
    public static SocketSession getSession(ChannelHandlerContext ctx) {
        return ctx.channel().attr(SocketSession.SESSION_KEY).get();
    }

    // 返回channel对应的SocketSession，channel->找session
    public static SocketSession getSession(Channel channel) {
        return channel.attr(SocketSession.SESSION_KEY).get();
    }

    public synchronized void set(String key,Object value) {
        map.put(key,value);
    }

    public synchronized <T> T get(String key) {
        return (T)map.get(key);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
