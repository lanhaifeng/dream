package com.feng.baseframework.model;

import lombok.Data;
import org.assertj.core.util.Lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: baseframework
 * @Description: 教师，测试用的实体
 * @Author: lanhaifeng
 * @CreateDate: 2018/10/23 20:25
 * @UpdateUser:
 * @UpdateDate: 2018/10/23 20:25
 * @UpdateRemark:
 * @Version: 1.0
 */
@Data
public class Teacher implements Serializable, Cloneable{
    private Long id;
    private String name;
    private Integer age;
    private List<Student> students;

    public Teacher() {
    }

    public Teacher(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Teacher(Long id, String name, Integer age, List<Student> students) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.students = students;
    }

    @Override
    public Teacher clone() throws CloneNotSupportedException {
        Teacher t = (Teacher)super.clone();
        if(t.getStudents() != null){
            t.setStudents(Lists.newArrayList(t.getStudents()));
        }
        return t;
    }
}
