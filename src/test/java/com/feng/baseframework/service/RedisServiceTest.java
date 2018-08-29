package com.feng.baseframework.service;

import com.feng.baseframework.test.BaseFrameworkApplicationTest;
import com.feng.baseframework.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

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

    @Test
    public void getHashMultValue() {
        String hashName = "hashTest";
        Set<String> keys = new HashSet<>();
        for(int i=0;i<100;i++){
            keys.add(String.valueOf(i));
            if(i==97){
                keys.add(String.valueOf(10001));
            }
        }
        keys.add(String.valueOf(10000));
        List<Object> data =redisService.getHashMultValue(hashName,keys,false);
        for(Object obj : data){
            if(obj instanceof Map){
                System.out.println("data is map!");
            }
            if(obj instanceof String){
                System.out.println(obj);
            }
        }
    }

    @Test
    public void setHashMultValue() throws JSONException {
        String hashName = "hashTest";
        Map<String,String> data = new HashMap<>();
        JSONObject jsonObject = null;
        for(int i=0;i<1000;i++){
            jsonObject = new JSONObject();
            jsonObject.put("id",StringUtil.generateUUID());
            jsonObject.put("name","name"+i);
            jsonObject.put("age","age"+i);
            jsonObject.put("sex","sex"+i);
            data.put(String.valueOf(i),jsonObject.toString());
        }

        redisService.setHashMultValue(hashName,data);
    }
}