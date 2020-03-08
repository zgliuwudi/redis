package com.redis.demo.redisdemo.producetest;

import com.redis.demo.redisdemo.util.JedisPoolUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: SubscribeQueue
 * @program: redis-demo
 * @Description: 订阅接收者
 * @Author: admin
 * @Date: 2020-03-08 23:02
 * @Version: 1.0
 **/
public class SubscribeQueue implements Runnable {

    private static String keyword = "publish:msg";
    private volatile int count = 0;

    public void subscribeMsg(Jedis jedis){

        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("channel="+channel+",msg="+message);
                super.onMessage(channel, message);
            }
        },keyword);

    }

    @Override
    public void run() {
        Jedis jedis = JedisPoolUtils.getJedis();
        subscribeMsg(jedis);
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        SubscribeQueue queue = new SubscribeQueue();
        executorService.execute(queue);

    }
}
