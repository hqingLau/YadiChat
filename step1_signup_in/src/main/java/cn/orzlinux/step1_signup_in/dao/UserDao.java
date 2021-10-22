package cn.orzlinux.step1_signup_in.dao;

import cn.orzlinux.step1_signup_in.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User selectUserByIdname(String idname);
    int insertUser(User user);
}
