package com.feng.baseframework.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable, Cloneable {
    private Integer id;
    private String name;
    private Integer age;
    private Teacher teacher;

    public  String getPrivateName(String name) {
        if (check()){
            return "private 被mock 了";
        }
        return name;
    }

    private Boolean check(){
        return false;
    }

    public Student() {
    }

    public Student(Integer id, String name, Integer age, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.teacher = teacher;
    }

    //浅拷贝
    @Override
    public Student clone() throws CloneNotSupportedException {
        return (Student)super.clone();
    }

    //深拷贝
    /*@Override
    public Student clone() throws CloneNotSupportedException {
        Student s = (Student)super.clone();
        if(s.getTeacher() != null){
            s.setTeacher(s.getTeacher().clone());
        }
        return s;
    }*/
}
