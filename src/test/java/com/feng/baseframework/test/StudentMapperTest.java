package com.feng.baseframework.test;

import com.feng.baseframework.mapper.StudentMapper;
import com.feng.baseframework.model.Student;
import com.github.pagehelper.PageHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @ProjectName: testProject
 * @Description: StudentMapper单元测试
 * @Author: lanhaifeng
 * @CreateDate: 2018/4/30 0:55
 * @UpdateUser:
 * @UpdateDate: 2018/4/30 0:55
 * @UpdateRemark:
 * @Version: 1.0
 */
public class StudentMapperTest extends BaseFrameworkApplicationTest {

    @Autowired
    private StudentMapper studentMapper;
    private Student student;

    @Before
    public void before(){
        System.out.println("-----------------------------");
        student = new Student(){
            {
                setName("tom");
                setAge(11);
            }
        };
        System.out.println("-----------------------------");
    }

    @Test
    public void addStudentTest(){
        studentMapper.addStudent(student);
        Assert.assertNotNull("新增学生记录失败,主键为空",student.getId());
    }

    @Test
    public void getStudentByIdTest(){
        Student temp = studentMapper.getStudentById(23);
        Assert.assertEquals("姓名不相同","tom",temp.getName());
    }

    @Test
    public void updateStudentTest(){

    }

    @Test
    public void getStudentsLikeNameTest(){
        PageHelper.startPage(1,5);
        List<Student> sudents = studentMapper.getStudentsLikeName("tom");
        Assert.assertNotNull("集合为空",sudents);
        Assert.assertTrue("名字不是以jack开头",sudents.get(0).getName().startsWith("jack"));
        System.out.println(sudents);
    }

    @Test
    public void deleteStudentsTest(){

    }
}
