package com.bjsxt.app;

import com.bjsxt.service.UserService;
import com.bjsxt.service.impl.UserServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * @author lvyelanshan
 * @create 2019-10-16 12:34
 */
public class ProviderApp {
    public static void main(String[] args) {
        /**
         * 完成远程服务的发布
         */
        //注册一个端口
        //将远程服务发布在本地的8888端口，别人将来要访问这个服务就需要通过8888这个端口
        try {
            LocateRegistry.createRegistry(8888);
            //发布的远程服务的访问的url
            String name = "rmi://localhost:8888/rmi";
            //一个提供具体服务的远程对象
            UserService userService = new UserServiceImpl();
            //给这个对象绑定一个远程的地址，相当于我们通过这个地址就可以获取到这个远程的对象,
            //获得到这个对象后就可以调用这个对象中的方法了
            Naming.bind(name,userService);
            //发布服务后java虚拟机挂起，等待远程消费者消费这个服务
            System.out.println("=========发布一个rmi远程服务========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
