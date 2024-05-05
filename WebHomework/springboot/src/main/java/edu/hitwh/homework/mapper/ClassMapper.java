package edu.hitwh.homework.mapper;

import edu.hitwh.homework.pojo.EntityClass;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 班级管理
 */
@Mapper
public interface ClassMapper {

    /**
     * 分页查询
     * @param className
     * @param begin
     * @param end
     * @return
     */
    List<EntityClass> list(String className, LocalDate begin, LocalDate end);

    /**
     * 根据id删除部门
     * @param id
     */
    @Delete("delete from classes where class_id = #{id}")
    void deleteById(Integer id);

    /**
     * 添加班级
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
}

