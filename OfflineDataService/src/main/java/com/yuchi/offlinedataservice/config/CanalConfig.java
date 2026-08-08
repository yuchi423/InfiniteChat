package com.yuchi.offlinedataservice.config;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Slf4j
@Configuration
public class CanalConfig {

    @Value("${canal.server.host}")
    private String canalHost;

    @Value("${canal.server.port}")
    private int canalPort;

    @Value("${canal.destination}")
    private String destination;

    @Value("${canal.username}")
    private String username;

    @Value("${canal.password}")
    private String password;

    @Value("${canal.filter}")
    private String filter;

    @Value("${canal.client.heartbeat.interval:2000}")
    private int heartbeatInterval;

    @Value("${canal.client.heartbeat.timeout:3000}")
    private int heartbeatTimeout;

    @Value("${canal.client.socket.timeout:6000}")
    private int socketTimeout;

    @Bean(destroyMethod = "disconnect")
    public CanalConnector canalConnector() {
        System.setProperty("canal.client.heartbeat.interval", String.valueOf(heartbeatInterval));
        System.setProperty("canal.client.heartbeat.timeout", String.valueOf(heartbeatTimeout));
        System.setProperty("canal.client.socket.timeout", String.valueOf(socketTimeout));

        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress(canalHost, canalPort),
                destination,
                username,
                password
        );
        connector.connect();
        log.info("Canal connected, destination={}", destination);
        connector.subscribe(filter);
        connector.rollback();
        return connector;
    }
}
