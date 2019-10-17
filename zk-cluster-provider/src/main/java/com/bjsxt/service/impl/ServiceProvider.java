package com.bjsxt.service.impl;

import com.bjsxt.constant.Constants;
import org.apache.zookeeper.*;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.CountDownLatch;

/**
 * @author lvyelanshan
 * @create 2019-10-17 13:19
 */
public class ServiceProvider {

    private CountDownLatch latch = new CountDownLatch(1);

    /**
     * 连接到zookeeper集群
     */
    public ZooKeeper connectZK(){


        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(Constants.ZK_HOST, Constants.ZK_TIME_OUT, new Watcher() {
                public void process(WatchedEvent event) {
                    //判断是否连接zookeeper集群
                    if(event.getState()==Event.KeeperState.SyncConnected){
                        latch.countDown();//连接成功时，线程唤醒，相当于将上面的CountDownLatch中的参数修改为0
                    }
                }
            });
            //使当前线程处于等待状态
            latch.await();//当CountDownLatch中的参数比0大时，线程挂起
        }catch (Exception e){
            e.printStackTrace();
        }
        return zk;
    }

    /**
     * 创建znode节点,将上面创建好的连接和发布服务的URL传进去
     * 创建这个节点的目的是为了保存这个URL（发布服务的URL）
     */
    public void createNode(ZooKeeper zk,String url){
        try {
            //参数：节点路径，保存到的数据，访问的权限，数据的模型
            byte[] data = url.getBytes();//将保存的数据URL转换为byte类型的字节数组
            //ZooDefs.Ids.OPEN_ACL_UNSAFE:表示不考虑访问权限，直接可以访问
            //CreateMode.EPHEMERAL_SEQUENTIAL:表示瞬时的并且是顺序的节点
            zk.create(Constants.ZK_RMI,data,ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 发布RMI服务:给一个远程对象绑定一个URL
     * remote：远程对象
     * host+port拼接成一个URL绑定到远程对象上
     */
    public String publishService(Remote remote,String host,int port){
        String url = null;
        try {
            //注册端口
            LocateRegistry.createRegistry(port);
            //绑定URL到远程对象上
            url = "rmi://" + host + ":" + port + "/rmiservice";
            Naming.bind(url,remote);
        }catch (Exception e){
            e.printStackTrace();
        }
        //返回服务的地址URL
        return url;
    }

    /**
     * 发布RMI服务，并且将服务的URL注册到zk集群中
     */
    public void pulish(Remote remote,String host,int port){
        //调用publicService,返回服务的URL，并将URL绑定到对应的远程对象上
        String url = publishService(remote,host,port);
        System.out.println(url);

        //如果发布的服务不是空的，就说明这个服务发布没有问题
        if(url!=null){
            //连接上zookeeper，将这个服务URL写入节点中
            ZooKeeper zk = connectZK();
            if (zk!=null){
                //如果获取到的zookeeper不为空，则表示连接到zookeeper集群
                //连接成功后将这个URL写入这个节点中
                createNode(zk,url);
            }
        }
    }
}
