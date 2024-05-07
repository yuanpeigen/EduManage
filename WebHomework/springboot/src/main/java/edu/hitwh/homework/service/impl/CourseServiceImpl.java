package edu.hitwh.homework.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.hitwh.homework.mapper.CourseMapper;
import edu.hitwh.homework.pojo.Course;
import edu.hitwh.homework.pojo.PageBean;
import edu.hitwh.homework.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;


    @Override
    public PageBean page(Integer pageNum, Integer pageSize, String courseName) {
        //1. 设置分页pagehelper参数
        PageHelper.startPage(pageNum, pageSize);
        //2. 执行查询
        List<Course> courseList = courseMapper.list(courseName);
        Page<Course> p = (Page<Course>) courseList;//这里是个强制类型转换
        //3. 封装PageBean对象,并返回
        return new PageBean(p.getTotal(), p.getResult());
    }

    @Override
    public void delete(List<Integer> ids) {
        courseMapper.delete(ids);
    }

    @Override
    public void save(Course course) {
        courseMapper.insert(course);
    }

    @Override
    public void update(Course course) {
        courseMapper.update(course);
    }

    @Override
    public List<Course> list() {
        return courseMapper.selectAll();
    }
}
