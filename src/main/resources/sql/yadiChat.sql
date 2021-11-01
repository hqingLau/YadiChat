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

# 建立全局聊天数据库
CREATE TABLE global_room_msg
(
    id INT NOT NULL AUTO_INCREMENT,
    id_name VARCHAR(128) NOT NULL ,
    nick_name VARCHAR(128) not null ,
    msg VARCHAR(10240) not null,
    msg_time DATETIME not null ,
    PRIMARY KEY (id),
    INDEX id_name(id_name)
);


# 现有用户所属数据库创建
delimiter EOF # 定义EO F为结束标志
drop procedure if exists create_table_from_current_users;
create procedure create_table_from_current_users()
begin
    declare idname varchar(256); # 遍历时临时idname
    #  若没有数据返回,程序继续,并将变量flag设为0
    declare flag int default 0;
    declare all_user_idname cursor for select id_name from user;
    declare continue handler for not found set flag=1;
    open all_user_idname;
        fetch all_user_idname into idname;
        while flag <> 1 do
            set @table_name = concat(idname,'_belong_group_friend');
            set @global_room = '\"global_room_msg\"';
            set @sql_t = concat('CREATE TABLE ',@table_name,'(
                `id` INT NOT NULL AUTO_INCREMENT,
                `type` INT NOT NULL DEFAULT 0,  # 0 为群组，1为好友
                `group_friend_name` VARCHAR(128) NOT NULL UNIQUE ,
                PRIMARY KEY (`id`),
                INDEX id_name(`type`)
            );');
            prepare sql_t from @sql_t;
            execute sql_t;
            fetch all_user_idname into idname;
        end while;
    close all_user_idname;
end
EOF
delimiter ;

call create_table_from_current_users();

# 群组信息，群的主人，对群有删除权限
create table groupMaster(
    id INT NOT NULL AUTO_INCREMENT,
    id_name VARCHAR(128) NOT NULL , # 存放所属用户名
    group_name VARCHAR(128) not null ,
    PRIMARY KEY (id),
    INDEX id_name(id_name),
    INDEX group_name(group_name)
)