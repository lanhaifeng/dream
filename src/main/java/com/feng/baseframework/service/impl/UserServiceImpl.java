package com.feng.baseframework.service.impl;

import com.feng.baseframework.model.User;
import com.feng.baseframework.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: baseframework
 * @Description: 用户业务实现类
 * @Author: lanhaifeng
 * @CreateDate: 2018/5/21 23:44
 * @UpdateUser:
 * @UpdateDate: 2018/5/21 23:44
 * @UpdateRemark:
 * @Version: 1.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Override
    public User addUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUsers(List<Integer> ids) {

    }

    @Override
    public void deleteUser(Integer id) {

    }

    @Override
    public User getUserById(Integer id) {
        return null;
    }

    @Override
    public User getUserByName(String userName) {
        return null;
    }
}
