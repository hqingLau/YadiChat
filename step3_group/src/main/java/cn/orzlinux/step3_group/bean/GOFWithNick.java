package cn.orzlinux.step3_group.bean;

public class GOFWithNick {
    int type; // 0 为群组，1为好友
    String groupFriendName;
    String nickname;

    public GOFWithNick(int type, String groupFriendName, String nickname) {
        this.type = type;
        this.groupFriendName = groupFriendName;
        this.nickname = nickname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGroupFriendName() {
        return groupFriendName;
    }

    public void setGroupFriendName(String groupFriendName) {
        this.groupFriendName = groupFriendName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
