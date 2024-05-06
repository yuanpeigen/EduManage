package edu.hitwh.homework.mapper;

import edu.hitwh.homework.pojo.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 班级管理
 */
@Mapper
public interface StudentMapper {
    /**
     * 条件查询
     * @param name
     * @param studentId
     * @param topDegree
     * @param classId
     * @return
     */
    List<Student> list(String name, Integer studentId, Integer topDegree, Integer classId);

    /**
     * 删除
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 更新学生信息
     * @param student
     */
    void update(Student student);

    /**
     * 新增学生
     * @param student
     */
    @Insert("insert into students(name, gender, student_id, phone, top_degree, date_of_birth, class_id, update_time) " +
            " values(#{name},#{gender},#{studentId},#{phone},#{topDegree},#{dateOfBirth},#{classId},#{updateTime})")
    void insert(Student student);

    /**
     * 查询所有学生
     * @return
     */
    @Select("select * from students")
    List<Student> selectAll();
}

