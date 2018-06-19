package com.feng.baseframework.jdk8.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: baseframework
 * @Description: 集合的stream操作
 * @Author: lanhaifeng
 * @CreateDate: 2018/5/14 22:53
 * @UpdateUser:
 * @UpdateDate: 2018/5/14 22:53
 * @UpdateRemark:
 * @Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class StreamTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * 使用流的方式实现，集合的subList方法，子集合与父集合无必然关系，当然，子集合中的元素引用与父集合相同元素的引用是同一个
     * 原生的subList方法返回的是父集合的视图，当修改子集合的结构，父集合的结构也会发生变化；
     * 子集合截取后，当父集合的结构发生变化，再使用子集合系统会抛出java.util.ConcurrentModificationException异常
     */
    @Test
    public void testSubList(){
        List<Object> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        List<Object> list1 = list.stream().filter((t)->t!=null).limit(2).collect(Collectors.toList());
        List<Object> list2 = list.subList(0,2);

        System.out.println(list1); //[1, 2]
        System.out.println(list2); //[1, 2]

        list1.remove(0);
        System.out.println(list1); //[2]
        System.out.println(list); //[1, 2, 3, 4]

        list2.remove(0);
        System.out.println(list2); //[2]
        System.out.println(list); //[2, 3, 4]

        list.add("5");
        System.out.println(list); //[2, 3, 4, 5]
        System.out.println(list1); //[2]
        expectedException.expect(ConcurrentModificationException.class);
        System.out.println(list2); //java.util.ConcurrentModificationException
    }
}
