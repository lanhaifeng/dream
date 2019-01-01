package com.feng.baseframework.model;

import org.hibernate.validator.constraints.NotEmpty;

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
public class User implements Serializable, Cloneable {

    private static final long serialVersionUID = 8031504569994478937L;

    public User(User user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.id = user.getId();
        this.name = user.getName();
    }
    public User(Builder builder) {
        this.userName = builder.userName;
        this.password = builder.password;
        this.id = builder.id;
        this.name = builder.name;
    }
    public User(){}

    static class Builder{
        private Integer id;

        private String userName;
        private String name;
        private String password;

        public Builder() {
        }

        public Builder withId(Integer id){
            this.id = id;
            return this;
        }
        public Builder withUserName(String userName){
            this.userName = userName;
            return this;
        }
        public Builder withName(String name){
            this.name = name;
            return this;
        }
        public Builder withPassword(String password){
            this.password = password;
            return this;
        }
        public User build(){
            return new User(this);
        }

    }

    private Integer id;
    @NotEmpty(message = "用户名为空")
    private String userName;
    private String name;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public User clone() throws CloneNotSupportedException {
        return (User)super.clone();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + ( id == null ? "" : id) +
                ", userName='" + ( userName == null ? "" : userName ) + '\'' +
                ", name='" + ( name == null ? ""  : name ) + '\'' +
                ", password='" + ( password == null ? ""  : password ) + '\'' +
                '}';
    }
}
