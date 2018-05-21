package com.feng.baseframework.model;

import java.io.Serializable;

/**
 * @ProjectName: baseframework
 * @Description: 用户角色实体类
 * @Author: lanhaifeng
 * @CreateDate: 2018/5/21 23:23
 * @UpdateUser:
 * @UpdateDate: 2018/5/21 23:23
 * @UpdateRemark:
 * @Version: 1.0
 */
public class UserRole implements Serializable {

    private static final long serialVersionUID = -6754737052139726895L;
    private Long id;

    private String role;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
