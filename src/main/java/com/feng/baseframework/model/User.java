package com.feng.baseframework.model;

import java.io.Serializable;

/**
 * @ProjectName: baseframework
 * @Description: 用户信息实体类
 * @Author: lanhaifeng
 * @CreateDate: 2018/5/21 23:22
 * @UpdateUser:
 * @UpdateDate: 2018/5/21 23:22
 * @UpdateRemark:
 * @Version: 1.0
 */
public class User implements Serializable {

    private static final long serialVersionUID = 8031504569994478937L;

    public User(User user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.id = user.getId();
        this.name = user.getName();
    }
    public User(){}
    private Long id;

    private String userName;
    private String name;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
