package com.bjsxt.app;

import com.bjsxt.client.UserServiceImpl;
import com.bjsxt.client.UserServiceImplService;

/**
 * @author lvyelanshan
 * @create 2019-10-16 15:43
 */
public class WsConsumerApp {
    public static void main(String[] args) {
        /*
        * 完成webservice服务的消费
        * */
        //创建服务类的对象
        UserServiceImplService service = new UserServiceImplService();

        //获得远程服务的代理对象
        UserServiceImpl userServiceImplPort = service.getUserServiceImplPort();

        System.out.println(userServiceImplPort.getClass().getName());

        //进行远程服务的调用
        String result = userServiceImplPort.sayHello("张三");

        System.out.println("result="+result);
    }
}
