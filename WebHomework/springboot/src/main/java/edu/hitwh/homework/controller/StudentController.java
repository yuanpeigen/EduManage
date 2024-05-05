package edu.hitwh.homework.controller;

import edu.hitwh.homework.pojo.PageBean;
import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.pojo.Student;
import edu.hitwh.homework.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 学生Controller
 */
@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       String name,
                       Integer studentId,
                       Integer topDegree,
                       Integer classId) {
        log.info("分页查询, 参数: {},{},{},{},{},{}", pageNum, pageSize, name, studentId, topDegree, classId);
        //调用service分页查询
        PageBean pageBean = studentService.page(pageNum, pageSize, name, studentId, topDegree, classId);
        return Result.success(pageBean);
    }


    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        log.info("批量删除操作, ids:{}", ids);
        studentService.delete(ids);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Student student) {
        log.info("新增学生,: {}", student);
        studentService.save(student);
        return Result.success();
    }


    @PutMapping
    public Result update(@RequestBody Student student) {
        log.info("更新学生信息 : {}", student);
        studentService.update(student);
        return Result.success();
    }
}
