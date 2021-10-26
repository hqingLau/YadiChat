package cn.orzlinux.step2_basic_communication.dao;

import cn.orzlinux.step2_basic_communication.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User selectUserByIdname(String idname);
    int insertUser(User user);
}
