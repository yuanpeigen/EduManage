package edu.hitwh.homework.service;

import edu.hitwh.homework.pojo.EntityClass;
import edu.hitwh.homework.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

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
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 新增班级
     * @param entityClass
     */
    void save(EntityClass entityClass);

    void update(EntityClass entityClass);

    /**
     * 查询全部班级
     * @return
     */
    List<EntityClass> list();
}
