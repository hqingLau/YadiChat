package cn.orzlinux.step2_basic_communication.bean;

public class User {
    private  String idName; // 唯一名id
    private String nickName;
    private String password;

    public User() {
    }

    public User(String idName, String nickName, String password) {
        this.idName = idName;
        this.nickName = nickName;
        this.password = password;
    }


    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
