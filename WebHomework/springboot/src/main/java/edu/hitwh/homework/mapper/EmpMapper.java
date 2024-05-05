package edu.hitwh.homework.mapper;

import edu.hitwh.homework.pojo.Emp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
@Mapper
public interface EmpMapper {

    /**
     * 条件查询
     * @param name
     * @param gender
     * @param begin
     * @param end
     * @return
     */
    List<Emp> list(String name, Short gender, LocalDate begin, LocalDate end);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 新增员工
     * @param emp
     */
    @Insert("insert into employees(name, gender, image, job, entrydate, department_id, create_time, update_time) " +
            " values(#{name},#{gender},#{image},#{job},#{entrydate},#{departmentId},#{createTime},#{updateTime})")
    void insert(Emp emp);


    /**
     * 更新员工
     * @param emp
     */
    void update(Emp emp);
    /**
     * 查询所有员工
     * @return
     */
    @Select("select * from employees where job = 1")
    List<Emp> selectAll();
}
