package com.bjsxt.service;

import com.bjsxt.constant.Constants;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lvyelanshan
 * @create 2019-10-17 16:04
 */
public class ServiceConsumer {

    //这个成员变量保存读取到的服务列表,volatile:表示某个线程修改URLs集合后，
    // 修改后的结果，对其他线程是可见的。
    private volatile List<String> urls = new ArrayList<String>();

    /**
     * CountDownLatch实现线程同步
     */
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

    //观察注册的/provider下节点中的数据是否有变化，
    // 如果注册节点发生变化，就要重新读取一次服务列表
    public void watchNode(final ZooKeeper zk){
        //获得/provider下的子节点
        try {
            //观察/provider节点下的子节点是否变化,返回的是子节点
            List<String> nodeList = zk.getChildren(Constants.ZK_REGISTRY, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    //子节点发生变化
                    if (watchedEvent.getType()==Event.EventType.NodeChildrenChanged){
                        //如果节点发生变化就重新调用这个方法再读取一次服务列表
                        watchNode(zk);
                    }
                }
            });
            //用于存放读取的节点下的地址数据
            List<String> dataList = new ArrayList<String>();
            for (String node:nodeList){
                //相当于获取“/provider/节点名”中的数据信息，返回的是一个字节数组
                byte[] data = zk.getData(Constants.ZK_REGISTRY + "/" + node, false, null);

                //将接收到的字节数组转换成String添加到list集合中
                dataList.add(new String(data));
            }
            //将dataList复制给urls,这样就读取到了服务下节点中的URL了
            urls=dataList;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获得RMI远程服务的对象
     */
    //返回的服务可能是各种服务的对象所以加入一个泛型
    public <T> T lookupService(String url){
        T remote = null;
        try {
            //获得远程服务的一个代理对象
            remote=(T) Naming.lookup(url);
        }catch (Exception e){
            //说明zookeeper集群没有连接通
            if (e instanceof ConnectException){
                //如果连接没有成功，但是urls中保存着之前存放的URL地址
                //则通过这个地址获取到服务
                if(urls.size()!=0){
                    url=urls.get(0);
                    return lookupService(url);
                }
            }
        }
        return remote;
    }

    /**
     * 查找RMI服务，通过随机算法产生一个URL
     */
    public <T extends Remote> T lookup(){
        T service=null;
        /*获得当前的URL数*/
        int size=urls.size();
        if(size>0){
            String url = null;
            if(size==1){
                url=urls.get(0);
            }else {
                //产生随机数(范围在size中)
                int random = ThreadLocalRandom.current().nextInt(size);
                //获得到这个随机数，取出这个URL
                 url = urls.get(random);
                System.out.println(url);
            }
            //将随机获得到的URL传递进去，返回service服务
            service = lookupService(url);
        }

        return service;

    }

    //构造器初始化对象
    public ServiceConsumer() {
        //创建对象时就连接zookeeper集群
        ZooKeeper zk = connectZK();
        //相当于给/provider节点添加了一个监听事件，监听这个节点的变化
        if(zk!=null){
            watchNode(zk);
        }
    }
}
