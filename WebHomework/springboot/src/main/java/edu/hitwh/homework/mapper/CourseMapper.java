package edu.hitwh.homework.mapper;

import edu.hitwh.homework.pojo.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 部门管理
 */
@Mapper
public interface CourseMapper {


    /**
     * 查询全部部门
     */
    List<Course> list(String courseName);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 新增课程
     * @param course
     */
    @Insert("insert into courses(course_name,start_time,end_time,days_of_week) " +
            " values(#{courseName},#{startTime},#{endTime},#{daysOfWeek})")
    void insert(Course course);

    /**
     * 更新课程信息
     * @param course
     */
    @Update("update courses set course_name=#{courseName},start_time=#{startTime},end_time=#{endTime},days_of_week=#{daysOfWeek} where course_id = #{courseId} ")
    void update(Course course);

    /**
     * 查询所有课表
     * @return
     */
    @Select("select * from courses")
    List<Course> selectAll();
}
