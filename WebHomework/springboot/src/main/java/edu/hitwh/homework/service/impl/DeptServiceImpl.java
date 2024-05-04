package edu.hitwh.homework.service.impl;

import edu.hitwh.homework.mapper.DeptMapper;
import edu.hitwh.homework.pojo.Dept;
import edu.hitwh.homework.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    private static final Logger log = LoggerFactory.getLogger(DeptServiceImpl.class);
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    @Override
    public void delete(Integer id) {
        deptMapper.deleteById(id);
    }

    @Override
    public void add(Dept dept) {
        //需要补全属性 updateTime
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);
    }

    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        log.info("777777777777777{}",dept);
        deptMapper.update(dept);
    }
}
