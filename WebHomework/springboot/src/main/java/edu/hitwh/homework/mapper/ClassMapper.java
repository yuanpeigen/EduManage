package edu.hitwh.homework.mapper;

import edu.hitwh.homework.pojo.EntityClass;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * 班级管理
 */
@Mapper
public interface ClassMapper {

    /**
     * 分页查询
     *
     * @param className
     * @param begin
     * @param end
     * @return
     */
    List<EntityClass> list(String className, LocalDate begin, LocalDate end);

    /**
     * 批量删除部门
     *
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 添加班级
     *
     * @param entityClass
     */
    @Insert("insert into classes(class_name, classroom, start_time, finish_time, classmaster_id) " +
            " values(#{className},#{classroom},#{startTime},#{finishTime},#{classmasterId})")
    void insert(EntityClass entityClass);

    /**
     * 修改班级
     * @param entityClass
     */
    void update(EntityClass entityClass);

    /**
     * 查询全部班级
     * @return
     */
    @Select("select * from classes")
    List<EntityClass> selectAll();
}

