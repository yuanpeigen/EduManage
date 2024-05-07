package edu.hitwh.homework.service;

import edu.hitwh.homework.pojo.PageBean;
import edu.hitwh.homework.pojo.Schedule;

import java.util.List;

/**
 * 部门管理
 */
public interface ScheduleService {
    /**
     * 分页查询
     * @return
     */
    PageBean page(Integer pageNum, Integer pageSize, Integer courseId,Integer teacherId,Integer classId);


    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Schedule> ids);

    /**
     * 新增班级
     * @param schedule
     */
    void save(Schedule schedule);

    void update(Schedule schedule);
}
