package com.feng.baseframework.service;

import com.feng.baseframework.test.BaseFrameworkApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class RedisServiceTest extends BaseFrameworkApplicationTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void remove() {
    }

    @Test
    public void set() {
    }

    @Test
    public void set1() {
    }

    @Test
    public void get() {
    }

    @Test
    public void multiSetList() {
        redisService.changeDb(1);
        String listName = "listTest";
        Collection<String> listData = new ArrayList<>();
        listData.add("list1");
        listData.add("list2");
        listData.add("list3");
        listData.add("list4");
        listData.add("list5");
        listData.add("list6");

        redisService.multiSetList(listName,listData);
    }

    @Test
    public void getRightListMultValueAfterDel() {
        multiSetList();
        redisService.changeDb(1);
        String listName = "listTest";
        List<Object> data =redisService.getRightListMultValueAfterDel(listName,8);
        if(data == null){
            return;
        }
        for (Object obj : data){
            System.out.println(obj);
        }
    }

    @Test
    public void getLeftListMultValueAfterDel() {
        multiSetList();
        redisService.changeDb(1);
        String listName = "listTest";
        List<Object> data =redisService.getLeftListMultValueAfterDel(listName,8);
        if(data == null){
            return;
        }
        for (Object obj : data){
            System.out.println(obj);
        }
    }
}