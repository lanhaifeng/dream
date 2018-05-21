package com.feng.baseframework.mapper;

import com.feng.baseframework.model.User;

import java.util.List;

/**
 * @ProjectName: baseframework
 * @Description: 用户操作数据接口
 * @Author: lanhaifeng
 * @CreateDate: 2018/5/21 23:45
 * @UpdateUser:
 * @UpdateDate: 2018/5/21 23:45
 * @UpdateRemark:
 * @Version: 1.0
 */
public interface UserMapper {

    public void addUser(User user);

    public void updateUser(User user);

    public void deleteUsers(List<Integer> ids);

    public void deleteUser(Integer id);

    public User getUserById(Integer id);

    public User getUserByName(String userName);
}
