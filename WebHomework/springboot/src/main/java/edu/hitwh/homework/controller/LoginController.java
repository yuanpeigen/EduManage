package edu.hitwh.homework.controller;

import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.pojo.User;
import edu.hitwh.homework.service.LoginService;
import edu.hitwh.homework.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        log.info("管理员登录,{}", user);
        User e = loginService.login(user);
        //登录成功,生成发送令牌
        if (e != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            String jwt = JwtUtils.generateJwt(claims);

            return Result.success(jwt);
        }
        //失败
        return e != null ? Result.success() : Result.error("用户名或密码错误");
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        log.info("管理员注册,{}", user);
        loginService.register(user);
        return Result.success();
    }
}
