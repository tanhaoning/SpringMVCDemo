package com.test.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.*;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tanzepeng on 2015/7/22.
 */
public class RedisTest {

    private JedisPoolConfig poolConfig = new JedisPoolConfig();
    private JedisPool jedisPool;
    private Jedis jedis;

    private List<JedisShardInfo> shardInfoList;
    private ShardedJedisPool shardedPool;
    private ShardedJedis shardedJedis;

    private static final int DEFAULT_TIMEOUT = 10;                //单位：seconds


    private static final String redisUrl = "redis://:@10.4.101.60:6379/0";
    private static final String urls = "redis://:@10.4.101.60:6379/0,redis://:@10.4.101.63:6380/0,redis://:@10.4.101.64:6381/0";

    // redis-server.exe redis.windows.conf
    // redis-cli.exe -h 192.168.10.61 -p 6379 -a "train"

    @Before
    public void init() {
        poolConfig.setMaxActive(100);
        poolConfig.setMaxIdle(300);
        poolConfig.setMinIdle(100);
        poolConfig.setTestOnBorrow(true);
    }

    @Test
    public void pressureShardSetTest() {
        List<JedisShardInfo> shardInfoList = new ArrayList<JedisShardInfo>();
        for (String url : urls.split(",")) {
            JedisShardInfo shardInfo = new JedisShardInfo(url);
            shardInfo.setTimeout(DEFAULT_TIMEOUT * 6 * 1000);
            shardInfoList.add(shardInfo);
        }
        this.shardInfoList = shardInfoList;
        shardedPool = new ShardedJedisPool(poolConfig, shardInfoList);
    }

    @Test
    public void pressureSetTest() throws InterruptedException {
        //jedisPool = new JedisPool(poolConfig, "10.4.101.60");
        List<JedisShardInfo> shardInfoList = new ArrayList<JedisShardInfo>();
        for (String url : urls.split(",")) {
            JedisShardInfo shardInfo = new JedisShardInfo(url);
            shardInfo.setTimeout(DEFAULT_TIMEOUT * 6 * 1000);
            shardInfoList.add(shardInfo);
        }
        this.shardInfoList = shardInfoList;
        shardedPool = new ShardedJedisPool(poolConfig, shardInfoList);

        class MyRunnable implements Runnable {
            public void run() {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("-------------------------------------------");
                System.out.println(Thread.currentThread().getId() + "  开始时间：" + df.format(new Date()));
                try {
                    //Jedis jedis = jedisPool.getResource();
                    shardedJedis = shardedPool.getResource();
                    int index = 0;
                    while (index++ < 500) {
                        shardedJedis.set("message" + index, "message" + index + ":" + new Date());
                    }
                    //jedisPool.returnResource(jedis);
                    shardedPool.returnResource(shardedJedis);

                } catch (Exception e) {
                    System.out.println("结束时间：" + df.format(new Date()) + " " + e);
                }
                System.out.println("结束时间：" + df.format(new Date()));
                System.out.println("-------------------------------------------");
            }
        }

        Thread t1 = new Thread(new MyRunnable());
        Thread t2 = new Thread(new MyRunnable());
        Thread t3 = new Thread(new MyRunnable());
        Thread t4 = new Thread(new MyRunnable());
        Thread t5 = new Thread(new MyRunnable());
        Thread t6 = new Thread(new MyRunnable());
        Thread t7 = new Thread(new MyRunnable());
        Thread t8 = new Thread(new MyRunnable());
        Thread t9 = new Thread(new MyRunnable());
        Thread t10 = new Thread(new MyRunnable());

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();
        t8.join();
        t9.join();
        t10.join();
        System.out.println("运行结束");
    }

    @Test
    public void getRedisInfo() {
        URI uri = URI.create(redisUrl);
        String host = uri.getHost();
        int post = uri.getPort();
        String password = uri.getUserInfo().split(":", 2)[1];
        int database = Integer.parseInt(uri.getPath().split("/", 2)[1]);

        System.out.println("**************host=" + host);
        System.out.println("**************post=" + post);
        System.out.println("**************password=" + password);
        System.out.println("**************database=" + database);
    }

    @Test
    public void redisSet() {
        jedis.set("id", "1");
        jedis.set("name", "hello");
        /**
         * mset相当于
         * jedis.set("name","minxr");
         * jedis.set("jarorwar","闵晓荣");
         */
        jedis.mset("name", "minxr", "jarorwar", "闵晓荣");
        System.out.println(jedis.mget("name", "jarorwar"));
    }

    @Test
    public void redisGet() {
        String id = jedis.get("id");
        String name = jedis.get("name");
        System.out.println("************id=" + id);
        System.out.println("************name=" + name);
    }

    @Test
    public void redisUpdate() {
        jedis.set("id", "2");
        jedis.append("name", "update");
    }

    /**
     * jedis操作Map
     */
    @Test
    public void testMap() {
        Map<String, String> user = new HashMap<String, String>();
        user.put("name", "minxr");
        user.put("pwd", "password");
        jedis.hmset("user", user);
//取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
//第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
        List<String> rsmap = jedis.hmget("user", "name");
        System.out.println(rsmap);
//删除map中的某个键值
//        jedis.hdel("user","pwd");
        System.out.println(jedis.hmget("user", "pwd")); //因为删除了，所以返回的是null
        System.out.println(jedis.hlen("user")); //返回key为user的键中存放的值的个数1
        System.out.println(jedis.exists("user"));//是否存在key为user的记录 返回true
        System.out.println(jedis.hkeys("user"));//返回map对象中的所有key  [pwd, name]
        System.out.println(jedis.hvals("user"));//返回map对象中的所有value  [minxr, password]
        Iterator<String> iter = jedis.hkeys("user").iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + jedis.hmget("user", key));
        }
    }

    /**
     * jedis操作List
     */
    @Test
    public void testList() {
//开始前，先移除所有的内容
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework", 0, -1));
//先向key java framework中存放三条数据
        jedis.lpush("java framework", "spring");
        jedis.lpush("java framework", "struts");
        jedis.lpush("java framework", "hibernate");
//再取出所有数据jedis.lrange是按范围取出，
// 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
        System.out.println(jedis.lrange("java framework", 0, -1));
    }

    /**
     * jedis操作Set
     */
    @Test
    public void testSet() {
//添加
        jedis.sadd("sname", "minxr");
        jedis.sadd("sname", "jarorwar");
        jedis.sadd("sname", "闵晓荣");
        jedis.sadd("sanme", "noname");
//移除noname
        jedis.srem("sname", "noname");
        System.out.println(jedis.smembers("sname"));//获取所有加入的value
        System.out.println(jedis.sismember("sname", "minxr"));//判断 minxr 是否是sname集合的元素
        System.out.println(jedis.srandmember("sname"));
        System.out.println(jedis.scard("sname"));//返回集合的元素个数
    }

    @Test
    public void test() throws InterruptedException {
//keys中传入的可以用通配符
        System.out.println(jedis.keys("*")); //返回当前库中所有的key  [sose, sanme, name, jarorwar, foo, sname, java framework, user, braand]
        System.out.println(jedis.keys("*name"));//返回的sname   [sname, name]
        System.out.println(jedis.del("sanmdde"));//删除key为sanmdde的对象  删除成功返回1 删除失败（或者不存在）返回 0
        System.out.println(jedis.ttl("sname"));//返回给定key的有效时间，如果是-1则表示永远有效
        jedis.setex("timekey", 10, "min");//通过此方法，可以指定key的存活（有效时间） 时间为秒
        Thread.sleep(5000);//睡眠5秒后，剩余时间将为<=5
        System.out.println(jedis.ttl("timekey"));   //输出结果为5
        jedis.setex("timekey", 1, "min");        //设为1后，下面再看剩余时间就是1了
        System.out.println(jedis.ttl("timekey"));  //输出结果为1
        System.out.println(jedis.exists("key"));//检查key是否存在             System.out.println(jedis.rename("timekey","time"));
        System.out.println(jedis.get("timekey"));//因为移除，返回为null
        System.out.println(jedis.get("time")); //因为将timekey 重命名为time 所以可以取得值 min
//jedis 排序
//注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
        jedis.del("a");//先清除数据，再加入数据进行测试
        jedis.rpush("a", "1");
        jedis.lpush("a", "6");
        jedis.lpush("a", "3");
        jedis.lpush("a", "9");
        System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
        System.out.println(jedis.sort("a")); //[1, 3, 6, 9]  //输入排序后结果
        System.out.println(jedis.lrange("a", 0, -1));
    }

    @After
    public void after() {

    }
}
