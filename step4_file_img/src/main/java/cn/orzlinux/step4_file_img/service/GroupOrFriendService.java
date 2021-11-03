package cn.orzlinux.step4_file_img.service;

import cn.orzlinux.step4_file_img.bean.GOFWithNick;
import cn.orzlinux.step4_file_img.bean.GroupOrFriend;
import cn.orzlinux.step4_file_img.bean.TextMsg;
import cn.orzlinux.step4_file_img.dao.GroupOrFriendDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Collections;
import java.util.List;

@Service
public class GroupOrFriendService {
    @Autowired
    GroupOrFriendDao dao;

    /**
     * 创建群，应执行的操作：
     * 1. groupmaster存储群主信息
     * 2、新建群表，用于存放聊天信息
     * 3、群主的所属群里加上这个群
     * @param idname
     * @param groupName
     * @return
     */
    @Transactional
    public int createNewGroup(String idname, String groupName, String groupNick) {
        try {
            dao.addNewGroupInfo2Master(idname,groupName,groupNick);
            dao.createGroupTable(groupName);
            dao.userAddGroupOrFriend(idname+"_belong_group_friend",new GroupOrFriend(0,groupName));
            return 1;
        }catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }

    int removeGroup(String idname,String groupName) {
        if(!dao.getGroupMaster(groupName).equals(idname)) {
            return 1;
        }
        try {
            dao.deleteGroupInfoFromMaster(idname,groupName);
            dao.dropGroupTable(groupName);
            dao.userDeleteGroupOrFriend(idname+"_belong_group_friend",new GroupOrFriend(0,groupName));
            return 1;
        }catch (Exception e) {
            return 0;
        }
    }

    public int insertMsg(String groupName, TextMsg textMsg) {
        return dao.insertMsg(groupName,textMsg);
    }

    public List<TextMsg> getLast100Msg(String group) {
        List<TextMsg> list = dao.getLast100Msg(group);
        Collections.reverse(list);
        return list;
    }

    public List<GOFWithNick> getUserBelongGroup(String idname) {
        return dao.getGroupOrFriend(idname+"_belong_group_friend");
    }

    /**
     * 加入组：
     *   用户的所属群里加上这个群
     * @param uid
     * @param gid
     * @return
     */
    public int joinRoom(String uid, String gid) {
        String master = dao.getGroupMaster(gid);
        if(master==null || master.equals("")) {
            return 0;
        }
        return dao.joinGroup(uid+"_belong_group_friend",gid);
    }
}
