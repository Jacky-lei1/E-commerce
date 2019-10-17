package com.bjsxt.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * 需要发布的服务对应的接口
 * @author lvyelanshan
 * @create 2019-10-16 15:09
 */
//加上这个注解的话，这
// 个接口中的所有public方法就会被发布成远程服务
@WebService
public interface UserService {

//    @WebMethod(exclude = true):用来限制某个方法是否要被发布
    public String sayHello(String name);

}
