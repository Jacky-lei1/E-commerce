package com.bjsxt.constant;

/**
 * 连接zookeeper集群的配置信息
 * @author lvyelanshan
 * @create 2019-10-17 13:02
 */
public interface Constants {
    /**
     * 结构：
     * provider
     *    | ---rmi00000001
     *    | ---rmi00000002
     *    | ---rmi00000003
     */
    /**
     * 接口中定义的都是静态常量，默认加上public static final
     */

    //访问zookeeper集群的连接的地址URL
    String ZK_HOST="192.168.43.131:2181,192.168.43.131:2182,192.168.43.131:2183";
    //连接zookeeper集群的超时时间
    int ZK_TIME_OUT = 5000;

    //zookeeper集群中注册服务的URL地址对应的永久节点
    String ZK_REGISTRY="/provider";

    //zookeeper集群中注册服务的URL地址的瞬时节点
    String ZK_RMI=ZK_REGISTRY+"/rmi";
}
