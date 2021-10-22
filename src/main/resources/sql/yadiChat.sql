USE YadiChat;
# 新建数据库 YadiChat
# 新建表user存放用户基本信息
CREATE TABLE user
(
    id INT NOT NULL AUTO_INCREMENT,
    id_name VARCHAR(128) NOT NULL, # 唯一的id名字
    nick_name VARCHAR(128) NOT NULL, # 昵称
    password VARCHAR(32) NOT NULL, # md5加密后的密码
    PRIMARY KEY(id),
    UNIQUE INDEX id_name(id_name),
    INDEX nick_name(nick_name)
);