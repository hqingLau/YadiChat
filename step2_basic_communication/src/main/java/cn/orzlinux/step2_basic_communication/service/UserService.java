package cn.orzlinux.step2_basic_communication.service;

import cn.orzlinux.step2_basic_communication.bean.User;
import cn.orzlinux.step2_basic_communication.dao.UserDao;
import cn.orzlinux.step2_basic_communication.tools.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User selectUserByIdname(String idname) {
        return userDao.selectUserByIdname(idname);
    }

    public User login(User user) {
        String idname = user.getIdName();
        String password = user.getPassword();

        User u1 = userDao.selectUserByIdname(idname);
        if(u1==null) {
            return null;
        }
        if (u1.getPassword().equals(MD5.getMd5(password))) {
            return u1;
        }
        return null;
    }

    public boolean regist(User user) {
        user.setPassword(MD5.getMd5(user.getPassword()));
        int x = userDao.insertUser(user);
        return x>0;
    }
}
