package cn.orzlinux.step3_group.dao;

import cn.orzlinux.step3_group.bean.GOFWithNick;
import cn.orzlinux.step3_group.bean.GroupOrFriend;
import cn.orzlinux.step3_group.bean.TextMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupOrFriendDao {
    // 用户的所属群组信息，应为_belong_group_friend格式
    int userAddGroupOrFriend(@Param("idname") String idname,@Param("groupOrFriend") GroupOrFriend groupOrFriend);
    int userDeleteGroupOrFriend(@Param("idname") String idname,@Param("groupOrFriend") GroupOrFriend groupOrFriend);
    List<GOFWithNick> getGroupOrFriend(@Param("idname") String idname);
    int addNewGroupInfo2Master(@Param("idname") String idname,@Param("groupName") String groupName,@Param("groupNick") String groupNick); // idname作为房主创建群（新建信息表、idname插入表名）
    int deleteGroupInfoFromMaster(@Param("idname") String idname,@Param("groupName") String groupName); // 删除群，应在service验证权限
    String getGroupMaster(@Param("groupName") String groupName);
    int joinGroup(@Param("idname") String idname,@Param("groupName") String groupName);

    int insertMsg(@Param("groupName")String groupName,@Param("textMsg") TextMsg textMsg);
    List<TextMsg> getLast100Msg(@Param("groupName") String groupName);

    int createGroupTable(@Param("groupName") String groupName);
    int dropGroupTable(@Param("groupName") String groupName);
}
