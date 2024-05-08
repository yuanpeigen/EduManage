package edu.hitwh.homework.mapper;

import edu.hitwh.homework.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 班级管理
 */
@Mapper
public interface LoginMapper {

@Select("select *from users where id=#{id} and password=#{password}")
    User login(User user);
}

