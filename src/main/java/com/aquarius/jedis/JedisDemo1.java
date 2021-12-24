package com.aquarius.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @Auther: dllAquarius
 * @Date: 2021/12/24
 * @Description:
 */
public class JedisDemo1 {

    public static void main(String[] args) {

        // 创建Jedis 对象
        Jedis jedis = new Jedis("192.168.0.108", 6379);
        // 测试
        String va = jedis.ping();
        System.out.println(va);
        jedis.close();
    }

    @Test
    public void demo1() {
        // 创建Jedis 对象
        Jedis jedis = new Jedis("192.168.0.108", 6379);
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
        System.out.println(jedis.exists("topn"));

//        System.out.println(jedis.get("topn"));
        jedis.set("name", "jack");
        jedis.expire("name", 10); //设置过期时间
        System.out.println(jedis.ttl("name"));
        System.out.println(jedis.get("name"));
        jedis.close();
    }

    /**
     * 多个string
     */
    @Test
    public void jedisDeomo2() {
        // 创建Jedis 对象
        Jedis jedis = new Jedis("192.168.0.108", 6379);
        // 设置多个key-val值
        jedis.mset("k1", "v1", "k2", "v2");
        List<String> mget = jedis.mget("k1", "k2");
        for (String s : mget) {
            System.out.println(s);
        }
        jedis.close();
    }

    /**
     * 操作list
     */
    @Test
    public void jedisDemo3() {
        // 创建Jedis 对象
        Jedis jedis = new Jedis("192.168.0.108", 6379);

        // 从左边压入值
        // 双向链表
        jedis.lpush("key1", "lucy", "jack", "tom");
        // 取出list中的值 0表示从左边第一个  -1表示右边第一个
        List<String> key1 = jedis.lrange("key1", 0, -1);
        for (String s : key1) {
            System.out.println(s);
        }
        jedis.close();

    }

    /**
     * 操作set
     */
    @Test
    public void jedisDomo4() {
        // 创建Jedis 对象
        Jedis jedis = new Jedis("192.168.0.108", 6379);

        jedis.sadd("name", "jack", "mary");
        // 取出set中的值
        Set<String> name = jedis.smembers("name");
        for (String s : name) {
            System.out.println(s);
        }

        // 删除set中的值
        jedis.srem("name", "jack");
        name = jedis.smembers("name");
        for (String s : name) {
            System.out.println(s);
        }
        jedis.close();
    }

    /**
     * 操作hash
     */
    @Test
    public void jedisDemo5() {
        // 创建Jedis 对象
        Jedis jedis = new Jedis("192.168.0.108", 6379);

        jedis.hset("users", "age", "20");
        String hget = jedis.hget("users", "age");
        System.out.println(hget);

        HashMap<String, String> map = new HashMap<>();
        map.put("tel", "123");
        map.put("age", "20");
        map.put("name", "tom");
        jedis.hmset("users2", map);
        List<String> users2 = jedis.hmget("users2", "tel", "age");
        for (String s : users2) {
            System.out.println(s);
        }
        jedis.close();
    }

    /**
     * zset有序集合
     */
    @Test
    public void jedisDemo6() {
        // 创建Jedis 对象
        Jedis jedis = new Jedis("192.168.0.108", 6379);

        jedis.zadd("china", 100d, "shanghai");

        Set<String> china = jedis.zrange("china", 0, -1);
        for (String s : china) {
            System.out.println(s);
        }
        jedis.close();

    }
}
