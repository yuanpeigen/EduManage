package edu.hitwh.homework.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/**
 * 班级实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityClass {
    private Integer classId; //班级ID
    private String className; //班级名称
    private String classroom; //教室
    private LocalDate startTime; //开班时间
    private LocalDate finishTime; //毕业时间
    private Integer classmasterId; //班主任ID

}
