package com.redis.demo.redisdemo;

import com.redis.demo.redisdemo.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisDemoApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {
        redisUtil.set("redis1", "abcde");
    }

    @Test
    void getValue(){
        Object redis1 = redisUtil.get("redis1");
        System.out.println(redis1);

    }

}
