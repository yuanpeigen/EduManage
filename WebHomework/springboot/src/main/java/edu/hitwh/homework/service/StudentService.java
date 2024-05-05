package edu.hitwh.homework.service;

import edu.hitwh.homework.pojo.PageBean;
import edu.hitwh.homework.pojo.Student;

import java.util.List;

/**
 * 学生管理
 */
public interface StudentService {

    /**
     * 分页查询
     * @return
     */
    PageBean page(Integer pageNum, Integer pageSize, String name, Integer studentId, Integer topDegree, Integer classId);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 更新学生信息
     * @param student
     */
    void update(Student student);

    /**
     * 新增学生
     * @param student
     */
    void save(Student student);
}
