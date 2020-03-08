package com.redis.demo.redisdemo.producetest;

import com.redis.demo.redisdemo.util.JedisPoolUtils;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName: CustomerQueue
 * @program: redis-demo
 * @Description: 消费值
 * @Author: admin
 * @Date: 2020-03-08 22:42
 * @Version: 1.0
 **/
public class CustomerQueue implements Runnable{

    private static String keyword = "producer1:msg";
    private volatile int count = 0;

    public void customerMsg(Jedis jedis){
        ++count;
        String lpop = jedis.lpop(keyword);
        System.out.println(Thread.currentThread().getName()+":name="+lpop+",count="+count);
    }

    @Override
    public synchronized void run() {

        Jedis jedis = JedisPoolUtils.getJedis();

        while (true){
            customerMsg(jedis);
        }

    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CustomerQueue customerQueue = new CustomerQueue();
        executorService.execute(customerQueue);
        executorService.execute(customerQueue);
        executorService.execute(customerQueue);
    }
}
