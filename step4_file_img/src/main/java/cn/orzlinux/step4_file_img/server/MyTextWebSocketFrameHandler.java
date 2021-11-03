package cn.orzlinux.step4_file_img.server;

import cn.orzlinux.step4_file_img.Utils.SpringUtil;
import cn.orzlinux.step4_file_img.bean.TextMsg;
import cn.orzlinux.step4_file_img.bean.User;
import cn.orzlinux.step4_file_img.service.GroupOrFriendService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
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
        System.out.println(msg.text());
        if(msg.text().equals("heartbeat")) {
            return;
        }
        SocketSession session = SocketSession.getSession(ctx);
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        Gson gson = new Gson();
        // 对于泛型无法直接获取class
        Map<String,String> map = gson.fromJson(msg.text(),typeToken.getType());
        User user = null;
        Map<String,String> result;
        TextMsg textMsg;
        switch (map.get("type")) {
            case "image":
                result = new HashMap<>();
                user = session.getUser();
                result.put("type","image");
                result.put("msg",map.get("msg"));
                result.put("url",map.get("url"));
                result.put("sendUserCookieId",session.getUserCookieId());
                result.put("sendUserNickname",user.getNickName());
                // 不想改数据库了，用魔数来判断是文件、图片、还是文本消息：

                String imageMsg = map.get("msg")+MsgTypeEnum.IMAGE+map.get("url");
                textMsg = new TextMsg(user.getIdName(),user.getNickName(),imageMsg,new Date());
                //textMsgService.insertMsg(textMsg);
                service.insertMsg(session.getGroup(),textMsg);
                SessionGroup.getInstance().sendToOthers(result,session);
                break;
            case "file":
                result = new HashMap<>();
                user = session.getUser();
                result.put("type","file");
                result.put("msg",map.get("msg"));
                result.put("url",map.get("url"));
                result.put("sendUserCookieId",session.getUserCookieId());
                result.put("sendUserNickname",user.getNickName());
                // 不想改数据库了，用魔数来判断是文件、图片、还是文本消息：

                String fileMsg = map.get("msg")+MsgTypeEnum.FILE+map.get("url");
                textMsg = new TextMsg(user.getIdName(),user.getNickName(),fileMsg,new Date());
                //textMsgService.insertMsg(textMsg);
                service.insertMsg(session.getGroup(),textMsg);
                SessionGroup.getInstance().sendToOthers(result,session);
                break;

            case "msg":
                result = new HashMap<>();
                user = session.getUser();
                result.put("type","msg");
                result.put("msg",map.get("msg"));
                result.put("sendUserCookieId",session.getUserCookieId());
                result.put("sendUserNickname",user.getNickName());
                textMsg = new TextMsg(user.getIdName(),user.getNickName(),map.get("msg"),new Date());
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
            //if(stateEvent.state()== IdleState.ALL_IDLE) {
            //    //ctx.writeAndFlush("heartbeat").addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
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
        //System.out.ln("客户端断开连接："+ctx.channel().localAddress().toString());
        super.channelInactive(ctx);
    }
}
