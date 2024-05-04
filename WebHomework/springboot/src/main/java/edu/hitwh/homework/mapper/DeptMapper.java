package edu.hitwh.homework.mapper;

import edu.hitwh.homework.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 部门管理
 */
@Mapper
public interface DeptMapper {

    /**
     * 查询全部部门
     */
    @Select("select * from departments")
    List<Dept> list();

    /**
     * 根据id删除部门
     * @param id
     */
    @Delete("delete from departments where department_id = #{id}")
    void deleteById(Integer id);

    /**
     * 新增部门
     * @param dept
     */
    @Insert("insert into departments(department_name,update_time) values (#{departmentName},#{updateTime})" )
    void insert(Dept dept);

    /**
     * 修改部门
     * @param dept
     */
    @Update("update departments set department_name=#{departmentName},update_time=#{updateTime} where department_id = #{departmentId} ")
    void update(Dept dept);
}
