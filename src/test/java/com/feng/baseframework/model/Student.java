package com.feng.baseframework.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {
    private Integer id;
    private String name;
    private Integer age;

    public  String getPrivateName(String name) {
        if (check()){
            return "private 被mock 了";
        }
        return name;
    }

    private Boolean check(){
        return false;
    }
}
