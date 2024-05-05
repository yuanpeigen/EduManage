package edu.hitwh.homework.controller;

import edu.hitwh.homework.pojo.Emp;
import edu.hitwh.homework.pojo.EntityClass;
import edu.hitwh.homework.pojo.PageBean;
import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.service.ClassService;
import edu.hitwh.homework.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 班级Controller
 */
@Slf4j
@RestController
@RequestMapping("/class")
public class ClassController {
    @Autowired
    private ClassService classService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       String className,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("分页查询, 参数: {},{},{},{},{}",pageNum,pageSize,className,begin,end);
        //调用service分页查询
        PageBean pageBean = classService.page(pageNum,pageSize,className,begin,end);
        return Result.success(pageBean);
    }


    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("批量删除操作, ids:{}",id);
        classService.delete(id);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody EntityClass entityClass){
        log.info("新增班级, emp: {}", entityClass);
        classService.save(entityClass);
        return Result.success();
    }


//    //选择班主任
//    @GetMapping("/all")
//    public Result list(){
//        log.info("查询所有员工信息");
//        List<Emp> list = empService.list();
//        return Result.success(list);
//    }

    @PutMapping
    public Result update(@RequestBody EntityClass entityClass){
        log.info("更新班级信息 : {}", entityClass);
        classService.update(entityClass);
        return Result.success();
    }
}
