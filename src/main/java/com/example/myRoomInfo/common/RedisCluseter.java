package com.example.myRoomInfo.common;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 测试rediscluster.
 * Created by daitian on 2017/5/31.
 */
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisCluseter {

    //节点
    private List<String> nodes;
    private int maxidle;
    private int maxTotal;
    private int minidle;
    private Long maxWaitMillis;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean testWhileIdle;

    //集群配置
    private String password;
    private int connectionTimeout;
    private int soTimeout;
    private int maxAttempts;

    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig() {

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(maxidle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMinIdle(minidle);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setTestOnBorrow(testOnBorrow);
        poolConfig.setTestOnReturn(testOnReturn);
        poolConfig.setTestWhileIdle(testWhileIdle);
        return poolConfig;
    }

    @Bean
    public JedisCluster jedisCluster() {

        Set<HostAndPort> snodes = new HashSet<>();
        for (Iterator<String> iterator = nodes.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            snodes.add(HostAndPort.parseString(next));
        }
//        return new JedisCluster(snodes, connectionTimeout, soTimeout, maxAttempts, password, genericObjectPoolConfig());
        return new JedisCluster(snodes);
    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public int getMaxidle() {
        return maxidle;
    }

    public void setMaxidle(int maxidle) {
        this.maxidle = maxidle;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMinidle() {
        return minidle;
    }

    public void setMinidle(int minidle) {
        this.minidle = minidle;
    }

    public Long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(Long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }
}
