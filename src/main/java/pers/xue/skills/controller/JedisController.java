package pers.xue.skills.controller;

import redis.clients.jedis.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author huangzhixue
 * @date 2021/8/16 3:58 下午
 * @Description
 */
public class JedisController {
    /**
     * redis 的pipeline 只跟客户端相关，并且由客户端实现
     * redis如果多次写读，每次读取响应花费很多时间，应该需要等待服务器响应了才能去读取。等待这就花费了主要的耗时
     * pipeline实现：就是改变读取方式，不是一次写一次读，而是一直写，一直读，一直读的话，除了第一次需要等待服务器响应外，都不需要进行等待
     * 读完所有操作后，在响应。
     * pipeline实现了缓存，redis服务器也实现了缓存
     * <p>
     * Redis的Pipeline和Transaction不同，Transaction会存储客户端的命令，最后一次性执行，而Pipeline则是处理一条，响应一条，但是这里却有一点，就是客户端会并不会调用read去读取socket里面的缓冲数据，这也就造就了，
     * 如果Redis应答的数据填满了该接收缓冲（SO_RECVBUF），那么客户端会通过ACK，WIN=0（接收窗口）来控制服务端不能再发送数据，那样子，数据就会缓冲在Redis的客户端应答列表里面。所以需要注意控制Pipeline的大小
     *
     * @param args
     */
    public static void main(String[] args) {
        // 连接池
        JedisPool jedisPool = new JedisPool("localhost", 6379);

        /* 操作Redis */
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.auth("123456");

            TimeLag t = new TimeLag();

            System.out.println("操作前，全部Key值：" + jedis.keys("*"));
            Pipeline p = jedis.pipelined();
            /* 插入多条数据 */
            for (Integer i = 0; i < 100000; i++) {
                Response<String> set = p.set(i.toString(), i.toString());
            }
            System.out.println("插入");

            /* 删除多条数据 */
            for (Integer i = 0; i < 100000; i++) {
                p.del(i.toString());
            }
            System.out.println("s");

            // 一次读取所有response.此时的Response<T>有数据
            List<Object> objects = p.syncAndReturnAll();

            System.out.println("操作前，全部Key值：" + jedis.keys("*"));

            System.out.println(t.cost());
            String set2 = jedis.set("111", "222");
            System.out.println(set2);


            //开启事务
            Transaction t1 = jedis.multi();
            //命令进入服务端的待执行队列
            Response<String> string = t1.get("string");
            Response<String> list = t1.lpop("list");
            Response<String> hash = t1.hget("hash", "foo");
            Response<Set<String>> zset = t1.zrange("zset", 0, -1);
            Response<String> set = t1.spop("set");
            //发送EXEC指令，执行所有命令，并返回结果
            t1.exec();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    static class TimeLag {

        private Date start;
        private Date end;

        public TimeLag() {
            start = new Date();
        }

        public String cost() {
            end = new Date();
            long c = end.getTime() - start.getTime();

            String s = new StringBuffer().append("cost ").append(c).append(" milliseconds (").append(c / 1000).append(" seconds).").toString();
            return s;
        }

        public static void main(String[] args) throws InterruptedException {
            TimeLag t = new TimeLag();
            TimeUnit.SECONDS.sleep(2);
            System.out.println(t.cost());
        }

    }
}

