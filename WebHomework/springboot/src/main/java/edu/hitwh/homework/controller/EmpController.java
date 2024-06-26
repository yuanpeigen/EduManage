package edu.hitwh.homework.controller;

import edu.hitwh.homework.pojo.Emp;
import edu.hitwh.homework.pojo.PageBean;
import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.service.EmpService;
import edu.hitwh.homework.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       String name, Short gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("分页查询, 参数: {},{},{},{},{},{}", pageNum, pageSize, name, gender, begin, end);
        //调用service分页查询
        PageBean pageBean = empService.page(pageNum, pageSize, name, gender, begin, end);
        return Result.success(pageBean);
    }


    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        log.info("批量删除操作, ids:{}", ids);
        empService.delete(ids);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Emp emp) {
        log.info("新增员工, emp: {}", emp);
        empService.save(emp);
        return Result.success();
    }


    //查询所有员工
    @GetMapping("/all")
    public Result list() {
        log.info("查询所有员工信息");
        List<Emp> list = empService.list();
        return Result.success(list);
    }

    @PutMapping
    public Result update(@RequestBody Emp emp) {
        log.info("更新员工信息 : {}", emp);
        empService.update(emp);
        return Result.success();
    }

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @PostMapping("/upload")
    public Result handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        log.info("文件上传,文件名:{}", file.getOriginalFilename());
        String url = aliOSSUtils.upload(file);
        log.info("文件上传成功,url:{}", url);
        return Result.success(url);
    }
}
