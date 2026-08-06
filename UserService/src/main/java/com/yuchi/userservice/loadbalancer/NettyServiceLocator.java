package com.yuchi.userservice.loadbalancer;

import com.yuchi.common.constant.CommonConstant;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NettyServiceLocator {

    private static final String NETTY_SERVICE_SCHEME = "ws://";

    @Resource
    private DiscoveryClient discoveryClient;

    public String getServiceInstance(String userId){
        List<ServiceInstance> instances = discoveryClient.getInstances(CommonConstant.DISCOVERY_CLIENT_NAME);
        if (instances.isEmpty()){
            return null;
        }
        ServiceInstance instance = new UrlHashLoadBalancer().select(instances, userId);
        return NETTY_SERVICE_SCHEME
                + instance.getHost()
                + ":"
                + instance.getPort()
                + CommonConstant.NETTY_SERVICE_URI;
    }
}
