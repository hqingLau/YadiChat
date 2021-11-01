package cn.orzlinux.step3_group.bean;

import java.util.Date;

public class TextMsg {
    private String idname;
    private String nick_name;
    private String msg;
    private Date msgTime;

    public TextMsg() {
    }

    public TextMsg(String idname, String nick_name, String msg, Date msgTime) {
        this.idname = idname;
        this.nick_name = nick_name;
        this.msg = msg;
        this.msgTime = msgTime;
    }

    @Override
    public String toString() {
        return "TextMsg{" +
                "idname='" + idname + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", msg='" + msg + '\'' +
                ", msgTime=" + msgTime +
                '}';
    }

    public String getIdname() {
        return idname;
    }

    public void setIdname(String idname) {
        this.idname = idname;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Date msgTime) {
        this.msgTime = msgTime;
    }
}
