package com.redis.demo.redisdemo.producetest;

import com.redis.demo.redisdemo.util.JedisPoolUtils;
import redis.clients.jedis.Jedis;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName: ProducerQueue
 * @program: redis-demo
 * @Description: 消息生产者
 * @Author: admin
 * @Date: 2020-03-08 21:49
 * @Version: 1.0
 **/
public class ProducerQueue implements Runnable {

    private static String keyword = "producer1:msg";
    // 不能使用这个作统计
    private volatile AtomicLong atomicLong = new AtomicLong(0);
    private volatile int count = 0;


    public void saveMsg(Jedis jedis, String msg) {
        ++count;
        atomicLong.incrementAndGet();
        Long rpush = jedis.rpush(keyword, msg);
        System.out.println(Thread.currentThread().getName() + ":size=" + rpush + ",count=" + count + ",atomiclong=" + atomicLong.incrementAndGet());

    }

    @Override
    public synchronized void run() {

        Jedis jedis = JedisPoolUtils.getJedis();

        for (int i = 0; i < 10; i++) {
            saveMsg(jedis, "msg" + i);
        }

    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ProducerQueue producerQueue = new ProducerQueue();
        executorService.execute(producerQueue);
        executorService.execute(producerQueue);
        executorService.execute(producerQueue);
        executorService.execute(producerQueue);
        executorService.execute(producerQueue);

//        ProducerQueue messageProducer = new ProducerQueue();
//        Thread t1 = new Thread(messageProducer, "thread1");
//        Thread t2 = new Thread(messageProducer, "thread2");
//        Thread t3 = new Thread(messageProducer, "thread3");
//        Thread t4 = new Thread(messageProducer, "thread4");
//        Thread t5 = new Thread(messageProducer, "thread5");
//        t1.start();
//        t2.start();
//        t3.start();
//        t4.start();
//        t5.start();
    }
}
