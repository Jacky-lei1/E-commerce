package com.bjsxt.app;

import com.bjsxt.service.ServiceConsumer;
import com.bjsxt.service.UserService;

import java.rmi.RemoteException;

/**
 * @author lvyelanshan
 * @create 2019-10-17 17:10
 */
public class zkClusterConsumerApp {
    public static void main(String[] args) throws InterruptedException, RemoteException {
        //创建这个对象，就相当于连接上了zookeeper集群，同时给/provider注册了一个监听事件
        ServiceConsumer serviceConsumer = new ServiceConsumer();

        while (true){ //重复消费服务
            //获得一个RMI远程服务对象
            UserService userService = serviceConsumer.lookup();

            //调用远程方法
            String result = userService.helloRmi("zhangsan");

            System.out.println(result);

            Thread.sleep(3000);//每隔三秒消费一次
        }
    }
}
