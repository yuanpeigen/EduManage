package edu.hitwh.homework.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 部门实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer studentId; //ID
    private String phone;//手机号
    private String name;//姓名
    private Integer classId;//班级id
    private LocalDate dateOfBirth;//出生日期
    private Integer gender;//性别
    private Integer topDegree;//最高学历
    private Integer disciplinaryNumber;//违纪次数
    private Integer disciplinaryScore;//违纪扣分
    private LocalDateTime updateTime; //修改时间
}
