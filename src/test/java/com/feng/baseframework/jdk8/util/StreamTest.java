package com.feng.baseframework.jdk8.util;

import com.feng.baseframework.model.Student;
import io.jsonwebtoken.lang.Assert;
import lombok.Getter;
import lombok.Setter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.function.BinaryOperator;
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

    @Test
    public void testSorted() {
        List<Student> students = new ArrayList<>();
        Student student = null;
        for (int i = 2; i < 10; i++) {
            student = new Student();
            student.setId(i);
            students.add(student);
        }

        List<Integer> ids =students.stream().sorted(Comparator.comparing(Student::getId).reversed()).map(s->s.getId()).collect(Collectors.toList());
        System.out.println(ids);
        ids =students.stream().map(s->s.getId()).collect(Collectors.toList());

        System.out.println(ids);
    }

    //测试lamblambda执行是否另起线程
    @Test
    public void testLambdaExecute() {
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        nums.add(3);

        int d=0;
        ThreadInfo mainThreadInfo = new ThreadInfo(Thread.currentThread());
        ThreadInfo lambda1ThreadInfo = new ThreadInfo();
        ThreadInfo lambda2ThreadInfo = new ThreadInfo();
        BinaryOperator<Integer> addFunction = (a,b)->{
            lambda1ThreadInfo.build(Thread.currentThread());
            return a+b+d;
        };

        addFunction.apply(1,2);

        CountDownLatch cdl = new CountDownLatch(1);

        new Thread(()->{
            lambda2ThreadInfo.build(Thread.currentThread());
            cdl.countDown();
        }).start();
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.state(mainThreadInfo.equals(lambda1ThreadInfo), "期待普通lambda表达式在主线程执行");
        Assert.state(!mainThreadInfo.equals(lambda2ThreadInfo), "期待普通线程lambda表达式在另一个线程执行");
    }

}

@Setter
@Getter
class ThreadInfo{

    public ThreadInfo() {
    }

    public ThreadInfo(Thread thread) {
        this.threadId = thread.getId();
        this.threadName = thread.getName();
        this.threadStr = thread.toString();
    }

    public void build(Thread thread){
        this.threadId = thread.getId();
        this.threadName = thread.getName();
        this.threadStr = thread.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(Objects.isNull(obj)) return false;
        boolean result = false;
        ThreadInfo target = (ThreadInfo)obj;
        if((getThreadId() == null && target.getThreadId() != null) || (getThreadId() != null && target.getThreadId() == null )) return false;
        if((getThreadName() == null && target.getThreadName() != null) || (getThreadName() != null && target.getThreadName() == null)) return false;
        if((getThreadStr() == null && target.getThreadStr() != null) || (getThreadStr() != null && target.getThreadStr() == null)) return false;
        if(getThreadId().equals(target.getThreadId()) && getThreadName().equals(target.getThreadName()) && getThreadStr().equals(target.getThreadStr())) result = true;
        return result;
    }

    private Long threadId;
    private String threadName;
    private String threadStr;
}