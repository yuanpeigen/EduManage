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
     * 根据ID查询员工
     * @param id
     * @return
     */
    @Select("select * from employees where  employee_id = #{id}")
    Emp getById(Integer id);

    /**
     * 更新员工
     * @param emp
     */
    void update(Emp emp);
    /**
     * 查询所有员工
     * @return
     */
    @Select("select * from employees")
    List<Emp> selectAll();
}
