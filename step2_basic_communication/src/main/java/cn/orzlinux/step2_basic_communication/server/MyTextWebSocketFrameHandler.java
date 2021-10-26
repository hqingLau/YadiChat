package cn.orzlinux.step2_basic_communication.server;

import cn.orzlinux.step2_basic_communication.bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakeException;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import org.apache.catalina.manager.util.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        SocketSession session = SocketSession.getSession(ctx);
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        Gson gson = new Gson();
        // 对于泛型无法直接获取class
        Map<String,String> map = gson.fromJson(msg.text(),typeToken.getType());
        User user = null;
        switch (map.get("type")) {
            case "msg":
                Map<String,String> result = new HashMap<>();
                user = session.getUser();
                result.put("type","msg");
                result.put("msg",map.get("msg"));
                result.put("sendUserId",user.getIdName());
                result.put("sendUserNickname",user.getNickName());
                SessionGroup.getInstance().sendToOthers(result,session);
                break;
            case "init":
                //String room = map.getOrDefault("room",null);
                String room = "群聊";
                session.setGroup(room);
                user = UserSession.get(map.getOrDefault("user",null));
                if(user==null) {
                    return;
                }
                session.setUser(user);
                SessionGroup.getInstance().addSession(session);
                break;
        }


    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 握手成功，升级为websocket协议
        if(evt== WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            new SocketSession(ctx.channel());
        } else if (evt instanceof IdleStateEvent) {
            IdleStateEvent stateEvent = (IdleStateEvent) evt;
            if(stateEvent.state()== IdleState.READER_IDLE) {
                ctx.channel().close();
            }
        } else {
            super.userEventTriggered(ctx,evt);
        }

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}