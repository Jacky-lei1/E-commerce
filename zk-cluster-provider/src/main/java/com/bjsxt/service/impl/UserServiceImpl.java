package com.bjsxt.service.impl;

import com.bjsxt.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**创建发布的服务对应的实现类
 * @author lvyelanshan
 * @create 2019-10-16 12:26
 */
public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    //因为父类中的这个无参构造方法已经显示的写了出来，所以这里子类也需要显示的写出来
    public UserServiceImpl() throws RemoteException {
        //在子类的无参构造方法中调用了父类的无参构造方法
        //父类的构造方法中调用了一个参数的构造方法，还有一个导出对象的方法：exportObject((Remote) this, port);
        //我们其实就是需要调用这个方法：exportObject((Remote) this, port);
        super();
    }

    public String helloRmi(String name) throws RemoteException {
        return "hello:"+name;
    }
}
