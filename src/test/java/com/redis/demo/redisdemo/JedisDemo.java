package com.redis.demo.redisdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: JedisDemo
 * @program: redis-demo
 * @Description: TODO
 * @Author: admin
 * @Date: 2020-03-08 11:47
 * @Version: 1.0
 **/
@SpringBootTest
public class JedisDemo {

    @Test
    public void connectTest(){
        Jedis jedis = new Jedis("192.168.1.15");
        System.out.println(jedis.ping());
    }

}
