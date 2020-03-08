package com.redis.demo.redisdemo.producetest;

import com.redis.demo.redisdemo.util.JedisPoolUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: PublishQueue
 * @program: redis-demo
 * @Description: 订阅发送者
 * @Author: admin
 * @Date: 2020-03-08 23:02
 * @Version: 1.0
 **/
public class PublishQueue implements Runnable {
    private static String keyword = "publish:msg";
    private volatile int count = 0;

    public void publishMsg(Jedis jedis,String msg){

        jedis.publish(keyword, "hello");

    }

    @Override
    public void run() {
        Jedis jedis = JedisPoolUtils.getJedis();
        for (int i = 0; i < 5; i++) {
            publishMsg(jedis,"msg"+i);
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        PublishQueue queue = new PublishQueue();
        executorService.execute(queue);

    }
}
