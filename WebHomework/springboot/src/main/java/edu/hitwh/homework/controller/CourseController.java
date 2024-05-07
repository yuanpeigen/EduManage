package edu.hitwh.homework.controller;

import edu.hitwh.homework.pojo.Course;
import edu.hitwh.homework.pojo.PageBean;
import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班级Controller
 */
@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       String courseName){
        log.info("分页查询, 参数: {},{},{}",pageNum,pageSize,courseName);
        //调用service分页查询
        PageBean pageBean = courseService.page(pageNum,pageSize,courseName);
        return Result.success(pageBean);
    }


    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("批量删除操作, ids:{}",ids);
        courseService.delete(ids);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Course course){
        log.info("新增课程, course: {}", course);
        courseService.save(course);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Course course){
        log.info("更新课程信息 : {}", course);
        courseService.update(course);
        return Result.success();
    }

    @GetMapping("/all")
    public Result list(){
        log.info("查询所有课表信息");
        List<Course> list = courseService.list();
        return Result.success(list);
    }
}
