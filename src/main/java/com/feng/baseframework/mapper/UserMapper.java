package com.feng.baseframework.mapper;

import com.feng.baseframework.SQLDriver.SimpleLangDriver;
import com.feng.baseframework.model.User;
import org.apache.ibatis.annotations.*;

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

    @Insert("insert into users(id,name,userName,password) values(#{id},#{name},#{userName},#{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public void addUser(User user);

    @Update("update users set name=#{name},password=#{password} where id=#{id}")
    public void updateUser(User user);

    @Delete("delete from users where id in(#{ids})")
    @Lang(SimpleLangDriver.class)
    public void deleteUsers(List<Integer> ids);

    @Delete("delete from users where id in(#{id})")
    public void deleteUser(Integer id);

    @Select("SELECT id, name, userName, password FROM users WHERE id = #{id}")
    @Results(id="userMap" , value = { @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "name", property = "name", javaType = String.class),
            @Result(column = "userName", property = "userName", javaType = String.class),
            @Result(column = "password", property = "password", javaType = String.class) })
    public User getUserById(Integer id);

    @Select("SELECT id, name, userName, password FROM users WHERE userName = #{userName}")
    @ResultMap(value = "userMap")
    public User getUserByName(String userName);
}
