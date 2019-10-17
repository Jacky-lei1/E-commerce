package com.bjsxt.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**创建需要发布的服务对应的业务接口
 * @author lvyelanshan
 * @create 2019-10-16 12:16
 */
/*Remote接口用于标识其方法可以从非本地虚拟机上调用的接口。
任何远程对象必须直接或间接实现此接口，才可以被远程调用
我们要达到的目的就是让这个接口中的方法从其他java虚拟机上调用*/
public interface UserService extends Remote {

    /*如果需要让这个方法被远程java虚拟机调用的话需要实现一个规范:声明一个远程异常*/
    public String helloRmi(String name) throws RemoteException;

}
