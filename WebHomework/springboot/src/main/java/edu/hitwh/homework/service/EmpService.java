package edu.hitwh.homework.service;

import edu.hitwh.homework.pojo.Emp;
import edu.hitwh.homework.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
public interface EmpService {
    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    PageBean page(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 新增员工
     * @param emp
     */
    void save(Emp emp);


    /**
     * 更新员工
     * @param emp
     */
    void update(Emp emp);

    /**
     * 查询所有员工
     * @return
     */
    List<Emp> list();
}