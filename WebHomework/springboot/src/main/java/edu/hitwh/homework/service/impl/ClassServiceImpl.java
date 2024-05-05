package edu.hitwh.homework.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.hitwh.homework.mapper.ClassMapper;
import edu.hitwh.homework.pojo.EntityClass;
import edu.hitwh.homework.pojo.PageBean;
import edu.hitwh.homework.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    @Override
    public PageBean page(Integer pageNum, Integer pageSize, String className, LocalDate begin, LocalDate end) {
        //1. 设置分页pagehelper参数
        PageHelper.startPage(pageNum, pageSize);
        //2. 执行查询
        List<EntityClass> classList = classMapper.list(className,begin,end);
        Page<EntityClass> p = (Page<EntityClass>) classList;//这里是个强制类型转换
        //3. 封装PageBean对象,并返回
        return new PageBean(p.getTotal(), p.getResult());
    }

    @Override
    public void delete(Integer id) {
        classMapper.deleteById(id);
    }

    @Override
    public void save(EntityClass entityClass) {
        classMapper.insert(entityClass);
    }

    @Override
    public void update(EntityClass entityClass) {
        classMapper.update(entityClass);
    }
}
