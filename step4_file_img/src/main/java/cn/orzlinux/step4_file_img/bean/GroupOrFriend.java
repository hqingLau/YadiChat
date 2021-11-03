package cn.orzlinux.step4_file_img.bean;

public class GroupOrFriend {
    int type; // 0 为群组，1为好友
    String groupFriendName;

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

    public GroupOrFriend(int type, String groupFriendName) {
        this.type = type;
        this.groupFriendName = groupFriendName;
    }

    public GroupOrFriend() {
    }
}
