package cn.orzlinux.step3_group.dao;

import cn.orzlinux.step3_group.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User selectUserByIdname(String idname);
    int insertUser(User user);
}
