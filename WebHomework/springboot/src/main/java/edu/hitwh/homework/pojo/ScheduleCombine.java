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
public class ScheduleCombine {
    private Integer scheduleCombineId; //ID
    private Integer courseId; //课程ID
    private String classIds;//班级ID
    private String teacherIds;//老师ID

}
