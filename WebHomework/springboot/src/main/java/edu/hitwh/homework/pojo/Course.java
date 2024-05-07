package edu.hitwh.homework.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/**
 * 课程实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private Integer courseId; //ID
    private String courseName; //课程名称
    private LocalTime startTime;//开课时间
    private LocalTime endTime;//节课时间
    private String daysOfWeek;//上课日
}
