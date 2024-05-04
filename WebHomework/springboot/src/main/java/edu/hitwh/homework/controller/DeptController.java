package edu.hitwh.homework.controller;

import edu.hitwh.homework.pojo.Dept;
import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/departments")
public class DeptController {


    @Autowired
    private DeptService deptService;

    /**
     * 查询部门
     *
     * @return 返回部门集合
     */
    @GetMapping
    public Result list() {
        log.info("查询全部部门信息");

        List<Dept> deptList = deptService.list();
        return Result.success(deptList);
    }

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("根据id删除部门:{}", id);
        //调用service删除部门
        deptService.delete(id);
        return Result.success();
    }

    /**
     * 增加部门
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Dept dept) {
        log.info("新增部门:{}", dept);
        //调用service新增部门
        deptService.add(dept);
        return Result.success();
    }

    /**
     * 修改部门
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Dept dept) {


        log.info("修改部门:{}", dept);
        //调用service修改部门
        deptService.update(dept);
        return Result.success();
    }

}
