package com.bjsxt.app;

import com.bjsxt.service.UserService;

import java.rmi.Naming;

/**
 * @author lvyelanshan
 * @create 2019-10-16 13:26
 */
public class ConsumerApp {
    public static void main(String[] args) {
        try {
            //发布的远程服务的访问URL
            String name = "rmi://localhost:8888/rmi";
            //通过发布的远程服务的URL，获得远程服务的代理对象(实际上是一个代理对象而不是一个userService对象)
            UserService userService = (UserService) Naming.lookup(name);
            System.out.println("获得远程服务的代理对象:"+userService.getClass().getName());
            //通过远程服务的代理对象调用远程服务的方法
            String rmi = userService.helloRmi("rmi");
            System.out.println("result="+rmi);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
