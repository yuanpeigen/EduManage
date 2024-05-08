package edu.hitwh.homework.controller;

import edu.hitwh.homework.pojo.PageBean;
import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.pojo.Schedule;
import edu.hitwh.homework.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班级Controller
 */
@Slf4j
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       Integer courseId,
                       Integer teacherId,
                       Integer classId){
        log.info("分页查询, 参数: {},{},{},{},{}",pageNum,pageSize,courseId,teacherId,classId);
        //调用service分页查询
        PageBean pageBean = scheduleService.page(pageNum,pageSize,courseId,teacherId,classId);
        return Result.success(pageBean);
    }

    //查询所有安排
    @GetMapping("/all")
    public Result list(){
        log.info("查询所有员工信息");
        List<Schedule> list = scheduleService.list();
        return Result.success(list);
    }


    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("批量删除操作, ids:{}",ids);
        scheduleService.delete(ids);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Schedule schedule){
        log.info("新增安排, schedule: {}", schedule);
        scheduleService.save(schedule);
        return Result.success();
    }


    @PutMapping
    public Result update(@RequestBody Schedule schedule){
        log.info("更新安排信息 : {}", schedule);
        scheduleService.update(schedule);
        return Result.success();
    }
}
