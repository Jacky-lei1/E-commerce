package com.bjsxt.app;

import com.bjsxt.service.UserService;
import com.bjsxt.service.impl.ServiceProvider;
import com.bjsxt.service.impl.UserServiceImpl;

import java.rmi.RemoteException;

/**
 * @author lvyelanshan
 * @create 2019-10-17 14:49
 */
public class ZkClusterProviderApp {
    public static void main(String[] args) throws RemoteException {
        //创建ServiceProvider对象
        ServiceProvider service = new ServiceProvider();
        //本地服务器对应的服务
        UserService userService = new UserServiceImpl();

        //调用发布服务方法进行服务的发布和注册
        //通过每次给定不同的端口发布RMI服务，这里开了三个java虚拟机，
        //将对应的7777,8888,9999端口的服务注册到了zookeeper集群中
        //由于发布服务的节点是临时节点，所以某个服务宕机（这里的某个虚拟机停止）则对应的节点就消失了
        //当再次注册该服务时又发布上去，那么消费者消费服务时，连接上zookeeper集群，就能够实时动态的获取到服务列表的信息
        service.pulish(userService,"localhost",9999);

    }
}
