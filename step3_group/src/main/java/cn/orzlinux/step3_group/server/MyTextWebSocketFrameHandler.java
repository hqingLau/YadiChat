package cn.orzlinux.step3_group.server;

import cn.orzlinux.step3_group.Utils.SpringUtil;
import cn.orzlinux.step3_group.bean.TextMsg;
import cn.orzlinux.step3_group.bean.User;
import cn.orzlinux.step3_group.service.GroupOrFriendService;
import cn.orzlinux.step3_group.service.TextMsgService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ChannelHandler.Sharable
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static GroupOrFriendService service;

    static {
        service = SpringUtil.getBean(GroupOrFriendService.class);
    }

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
                result.put("sendUserCookieId",session.getUserCookieId());
                result.put("sendUserNickname",user.getNickName());
                TextMsg textMsg = new TextMsg(user.getIdName(),user.getNickName(),map.get("msg"),new Date());
                //textMsgService.insertMsg(textMsg);
                service.insertMsg(session.getGroup(),textMsg);
                SessionGroup.getInstance().sendToOthers(result,session);
                break;
            case "init":
                String room = map.getOrDefault("group",null);
                session.setGroup(room);
                String userCookieId = map.getOrDefault("user",null);
                user = UserSession.get(userCookieId);
                if(user==null) {
                    return;
                }
                session.setUserCookieId(userCookieId);
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
            //IdleStateEvent stateEvent = (IdleStateEvent) evt;
            //if(stateEvent.state()== IdleState.READER_IDLE) {
            //    ctx.channel().close();
            //}
        } else {
            super.userEventTriggered(ctx,evt);
        }

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("客户端断开连接："+ctx.channel().localAddress().toString());
        super.channelInactive(ctx);
    }
}
