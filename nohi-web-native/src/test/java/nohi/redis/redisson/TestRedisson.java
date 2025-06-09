package nohi.redis.redisson;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.api.listener.MessageListener;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

import java.util.concurrent.TimeUnit;

/**
 * redisson测试
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRedisson {
    private static RedissonClient redisson;

    @BeforeAll
    public static void init() {
        Config config = new Config();
        // TransportMode.EPOLL 只能在Linux下使用
        config.setTransportMode(TransportMode.NIO);
        // 设置编码方式
        config.setCodec(new StringCodec());
        // useSentinelServers 配置哨兵模式连接,address为哨兵端口
        // useClusterServers 集群模式
        // useSingleServer 单节点模式
        // useMasterSlaveServers 主从模式
        config.useSentinelServers()
                .setMasterName("mymaster")
                .addSentinelAddress("redis://10.0.0.100:26379")
                .addSentinelAddress("redis://10.0.0.101:26379")
                .addSentinelAddress("redis://10.0.0.102:26379").setPassword("PasswordABC")
        ;
        redisson = Redisson.create(config);
        RBucket<Object> obj = redisson.getBucket("bucket");
        obj.set("this is bucket in INIT");
        log.debug("obj:{}", obj);
        log.debug("obj:{}", obj.get());
    }

    @Test
    @DisplayName("测试字符串")
    public void testString() {
        // Bucket 可以存任何值
        RBucket<String> bucket = redisson.getBucket("name");
        String name = bucket.get();
        log.debug("name1:{}", name);
        if (StringUtils.isBlank(name)) {
            name = bucket.getAndSet(DateUtil.now());
            log.debug("name2:{}", name);
            name = bucket.getAndSet(DateUtil.now());
            log.debug("name3:{}", name);
        } else {
            name = bucket.getAndSet(DateUtil.now());
            log.debug("name4:{}", name);
            name = bucket.get();
            log.debug("name5:{}", name);
        }
    }

    @Test
    @DisplayName("测试哈希")
    public void testHashes() {
        // Bucket 可以存任何值
        RMap<String, String> map = redisson.getMap("HashMap");

        map.put("id", DateUtil.now());
        map.put("date", DateUtil.now());

        map.expire(300, TimeUnit.SECONDS);

        log.debug("id:{}", redisson.getMap("HashMap").get("id"));
        log.debug("date:{}", redisson.getMap("HashMap").get("date"));

    }

    @Test
    @DisplayName("测试列表")
    public void testLists() {
        // Bucket 可以存任何值
        RList<Object> list = redisson.getList("List");
        list.add("1");
        list.add("3");
        list.add("2");
        list.add("4");
        list.add(11);
        list.add(12);

        list.expire(300, TimeUnit.SECONDS);

        list = redisson.getList("List");
        log.debug("size :{}", list.size());
        log.debug("id 0:{}", list.toString());

    }

    @Data
    static class TopicMessage {
        private String id;
        private String msg;

        public TopicMessage() {
        }

        public TopicMessage(String id, String msg) {
            this.id = id;
            this.msg = msg;
        }
    }

    /**
     * 消息订单，使用main函数，不会自动退出
     * 使用Test需要增加死循环
     * <p>
     * 需要统一发布、订阅的编码方式
     *
     * @param args
     */
    public static void main(String[] args) {
        init();

        // Bucket 可以存任何值
        RTopic topic = redisson.getTopic("Topic", new JsonJacksonCodec());
        int id = topic.addListener(TopicMessage.class, new MessageListener<TopicMessage>() {
            @Override
            public void onMessage(CharSequence channel, TopicMessage msg) {
                log.debug("Got message:{}", msg);
                System.out.println("Got something:" + msg);
            }
        });
        log.info("id:{}", id);

        // topic.removeListener(id);
    }

    @Test
    @DisplayName("消息订阅")
    public void testTopid() throws InterruptedException {
        // Bucket 可以存任何值
        RTopic topic = redisson.getTopic("Topic", new JsonJacksonCodec());
        int id = topic.addListener(TopicMessage.class, new MessageListener<TopicMessage>() {
            @Override
            public void onMessage(CharSequence channel, TopicMessage msg) {
                log.debug("Got message:{}", msg);
                System.out.println("Got something:" + msg);
            }
        });
        log.info("id:{}", id);

        int i = 0;
        while (true) {
            TimeUnit.SECONDS.sleep(1);
            i++;
            if (i > 100) {
                log.warn("消息订阅退出");
                break;
            }
        }
    }

    @Test
    @DisplayName("消息发布")
    public void testTopidPublis() throws InterruptedException {
        // Bucket 可以存任何值
        RTopic topic = redisson.getTopic("Topic", new JsonJacksonCodec());
        TopicMessage msg = new TopicMessage();
        msg.setId("1");
        msg.setMsg("黄河 黄河 这里是黄河...");
        long receiverClients = topic.publish(msg);
        log.info("receiverClients:{}", receiverClients);
    }


    @Test
    @DisplayName("可重入锁")
    public void testRlock() throws InterruptedException {
        // 获取锁
        RLock lock = redisson.getLock("Rlock");

        try {
            // 加锁
            // 只锁10钞
            // lock.lock(10, TimeUnit.SECONDS);
            lock.lock();
            log.info("加锁成功...");
            TimeUnit.SECONDS.sleep(50);
            log.info("业务处理完成...");
        } catch (Exception e) {
            log.error("异常:{}", e.getMessage());
        } finally {
            lock.unlock();
            log.info("释放锁...");
        }
    }
}

