package edu.hitwh.homework.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;//管理员id
    private String password;//管理员密码
    private String name;
    private Integer status;//状态
}
