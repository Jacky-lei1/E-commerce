package com.bjsxt.service.impl;

import com.bjsxt.service.UserService;

import javax.jws.WebService;

/**
 * 发布服务对应的实现类
 * @author lvyelanshan
 * @create 2019-10-16 15:14
 */
@WebService
public class UserServiceImpl implements UserService {

    public String sayHello(String name) {
        return "hello:"+name;
    }
}
