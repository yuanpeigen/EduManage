package edu.hitwh.homework.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.hitwh.homework.mapper.StudentMapper;
import edu.hitwh.homework.pojo.PageBean;
import edu.hitwh.homework.pojo.Student;
import edu.hitwh.homework.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public PageBean page(Integer pageNum, Integer pageSize, String name, Integer studentId, Integer topDegree, Integer classId) {
        //1. 设置分页pagehelper参数
        PageHelper.startPage(pageNum, pageSize);
        //2. 执行查询
        List<Student> StudentList = studentMapper.list(name,studentId,topDegree,classId);
        Page<Student> p = (Page<Student>) StudentList;//这里是个强制类型转换
        //3. 封装PageBean对象,并返回
        return new PageBean(p.getTotal(), p.getResult());
    }

    @Override
    public void delete(List<Integer> ids) {
        studentMapper.delete(ids);
    }

    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
    }

    @Override
    public void save(Student student) {
        student.setDisciplinaryNumber(0);
        student.setDisciplinaryScore(0);
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.insert(student);
    }

    @Override
    public List<Student> list() {
        return studentMapper.selectAll();
    }


}
