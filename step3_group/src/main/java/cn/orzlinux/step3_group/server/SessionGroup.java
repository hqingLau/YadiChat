package cn.orzlinux.step3_group.server;

import com.google.gson.Gson;
import com.mysql.cj.util.StringUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionGroup {
    private static SessionGroup instance;

    // 组名到组的映射
    private ConcurrentHashMap<String, ChannelGroup> groupMap = new ConcurrentHashMap<>();

    private SessionGroup() {}
    public static SessionGroup getInstance() {
        if(instance==null) {
            instance = new SessionGroup();
        }
        return instance;
    }

    public void sendToOthers(Map<String, String> result, SocketSession session) {
        ChannelGroup group = groupMap.get(session.getGroup());
        if(null == group) {
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(result);
        ChannelGroupFuture future = group.writeAndFlush(new TextWebSocketFrame(json));
        future.addListener(f->{
            System.out.println("完成发送："+json);
        });
    }

    public void addSession(SocketSession session) {
        String groupName = session.getGroup();
        if(StringUtils.isNullOrEmpty(groupName)) {
            return;
        }
        ChannelGroup group = groupMap.get(groupName);
        if(null==group) {
            group = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
            groupMap.put(groupName,group);
        }
        group.add(session.getChannel());
    }

    /**
     * 关闭连接
     * @param session
     * @param echo
     */
    public void closeSession(SocketSession session,String echo) {
        ChannelFuture sendFuture = session.getChannel().writeAndFlush(new TextWebSocketFrame(echo));
        sendFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("连接关闭："+echo);
                channelFuture.channel().close();
            }
        });
    }

    /**
     * 发送消息
     */
    public void sendMsg(ChannelHandlerContext ctx,String msg) {
        ChannelFuture sendFuture = ctx.writeAndFlush(new TextWebSocketFrame(msg));
        sendFuture.addListener(f->{
            System.out.println("对所有发送完成："+msg);
        });
    }

    public void shutdownGracefully() {
        Iterator<ChannelGroup> groupIterator = groupMap.values().iterator();
        while(groupIterator.hasNext()) {
            groupIterator.next().close();
        }
    }
}
