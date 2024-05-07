package edu.hitwh.homework.service;

import edu.hitwh.homework.pojo.Course;
import edu.hitwh.homework.pojo.PageBean;

import java.util.List;

/**
 * 部门管理
 */
public interface CourseService {
    /**
     * 分页查询
     * @return
     */
    PageBean page(Integer pageNum, Integer pageSize, String courseName);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 新增课程
     * @param course
     */
    void save(Course course);

    /**
     * 修改课程
     * @param course
     */
    void update(Course course);

    /**
     * 查询所有课表
     * @return
     */
    List<Course> list();
}
