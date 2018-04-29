package com.feng.baseframework.mapper;

import org.apache.ibatis.jdbc.SQL;

public class ProviderMethod {

    public String studentsLikeName(String name){
        return new SQL(){
            {
                super.SELECT("id,name,age");
                super.FROM("students");
                super.WHERE("name like "+name+"%");
            }
        }.toString();
    }
}
