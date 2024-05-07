package edu.hitwh.homework.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Integer scheduleId; //ID
    private Integer courseId; //课程ID
    private Integer classId;//班级ID
    private Integer teacherId;//老师ID

}
