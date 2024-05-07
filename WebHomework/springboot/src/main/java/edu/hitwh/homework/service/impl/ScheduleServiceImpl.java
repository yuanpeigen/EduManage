package edu.hitwh.homework.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.hitwh.homework.mapper.ScheduleMapper;
import edu.hitwh.homework.pojo.PageBean;
import edu.hitwh.homework.pojo.Schedule;
import edu.hitwh.homework.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public PageBean page(Integer pageNum, Integer pageSize, Integer courseId,Integer teacherId,Integer classId) {
        //1. 设置分页pagehelper参数
        PageHelper.startPage(pageNum, pageSize);
        //2. 执行查询
        List<Schedule> scheduleList = scheduleMapper.list(courseId,teacherId,classId);
        Page<Schedule> p = (Page<Schedule>) scheduleList;//这里是个强制类型转换
        //3. 封装PageBean对象,并返回
        return new PageBean(p.getTotal(), p.getResult());
    }

    @Override
    public void delete(List<Schedule> ids) {
        scheduleMapper.delete(ids);
    }

    @Override
    public void save(Schedule schedule) {
        scheduleMapper.insert(schedule);
    }

    @Override
    public void update(Schedule schedule) {
        scheduleMapper.update(schedule);
    }
}
