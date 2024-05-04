package edu.hitwh.homework.controller;

import edu.hitwh.homework.pojo.Emp;
import edu.hitwh.homework.pojo.PageBean;
import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmpController {
    @Autowired
    private EmpService empService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, Short gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("分页查询, 参数: {},{},{},{},{},{}",pageNum,pageSize,name,gender,begin,end);
        //调用service分页查询
        PageBean pageBean = empService.page(pageNum,pageSize,name,gender,begin,end);
        return Result.success(pageBean);
    }


    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("批量删除操作, ids:{}",ids);
        empService.delete(ids);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Emp emp){
        log.info("新增员工, emp: {}", emp);
        empService.save(emp);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询员工信息, id: {}",id);
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }

    //选择班主任
    @GetMapping("/all")
    public Result list(){
        log.info("查询所有员工信息");
        List<Emp> list = empService.list();
        return Result.success(list);
    }

    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("更新员工信息 : {}", emp);
        empService.update(emp);
        return Result.success();
    }
}
