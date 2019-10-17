package com.bjsxt.app;

import com.bjsxt.service.UserService;
import com.bjsxt.service.impl.UserServiceImpl;

import javax.xml.ws.Endpoint;

/**
 * @author lvyelanshan
 * @create 2019-10-16 15:15
 */
public class WsProviderApp {
    public static void main(String[] args) {
        /**
         * 完成webService服务的发布
         */
        //发布WebService服务的访问地址
        String address = "http://localhost:9999/ws";
        //创建UserService对象
        UserService userService = new UserServiceImpl();
        //发布具体的webService服务
        Endpoint.publish(address,userService);

        System.out.println("-------发布WebService服务-------");
    }
}
