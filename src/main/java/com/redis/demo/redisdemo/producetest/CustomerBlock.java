package com.redis.demo.redisdemo.producetest;

import com.redis.demo.redisdemo.util.JedisPoolUtils;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: CustomerQueue
 * @program: redis-demo
 * @Description: 消费值-阻塞队列模式
 * @Author: admin
 * @Date: 2020-03-08 22:42
 * @Version: 1.0
 **/
public class CustomerBlock implements Runnable{

    private static String keyword = "producer1:msg";
    private volatile int count = 0;

    public void customerMsg(Jedis jedis){
        ++count;
        List<String> lpop = jedis.blpop(0,keyword);
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
        CustomerBlock customerQueue = new CustomerBlock();
        executorService.execute(customerQueue);
        executorService.execute(customerQueue);
        executorService.execute(customerQueue);
    }
}
