package edu.hitwh.homework.service;

import edu.hitwh.homework.pojo.EntityClass;
import edu.hitwh.homework.pojo.PageBean;

import java.time.LocalDate;

/**
 * 部门管理
 */
public interface ClassService {
    /**
     * 分页查询
     * @return
     */
    PageBean page(Integer pageNum, Integer pageSize, String className, LocalDate begin, LocalDate end);


    /**
     * 批量删除
     * @param id
     */
    void delete(Integer id);

    /**
     * 新增班级
     * @param entityClass
     */
    void save(EntityClass entityClass);

    void update(EntityClass entityClass);
}
