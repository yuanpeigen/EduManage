package edu.hitwh.homework.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页查询结果的封装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean {

    private Long total;//总记录数
    private List row;//数据列表
}
