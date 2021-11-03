package cn.orzlinux.step4_file_img.dao;

import cn.orzlinux.step4_file_img.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User selectUserByIdname(String idname);
    int insertUser(User user);
}
