package edu.hitwh.homework.mapper;

import edu.hitwh.homework.pojo.Schedule;
import edu.hitwh.homework.pojo.ScheduleCombine;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 班级管理
 */
@Mapper
public interface ScheduleMapper {

    /**
     * 分页查询
     * @return
     */
    List<ScheduleCombine> list(Integer courseId, Integer teacherId, Integer classId);

    /**
     * 批量删除安排
     *
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 添加安排
     * @param schedule
     */
    @Insert("insert into course_schedules(class_id, course_id, teacher_id) " +
            " values(#{classId},#{courseId},#{teacherId})")
    void insert(Schedule schedule);

    /**
     * 修改安排
     * @param schedule
     */
    void update(Schedule schedule);

    /**
     * 查询全部安排
     */
    @Select("select * from course_schedules")
    List<Schedule> selectAll();
}

