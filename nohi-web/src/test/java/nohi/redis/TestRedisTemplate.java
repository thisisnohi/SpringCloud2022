package nohi.redis;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>项目名称: nohi-web </p>
 * <p>文件名称: TestRedisTemplate </p>
 * <p>功能描述: TODO </p>
 * <p>创建时间: 2025/1/22 </p>
 *
 * @author NOHI
 * @version v1.0
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRedisTemplate {

    private static RedisTemplate<String, String> template;

    @BeforeAll
    public static void init() {
//        // 单机模式
//        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();
//        rsc.setPort(6379);
//        rsc.setPassword("PasswordABCD");
//        rsc.setHostName("10.0.0.100");
//
//        // 集群模式
//        RedisClusterConfiguration rcc = new RedisClusterConfiguration();
//        rcc.setPassword("PasswordABCD");
//        List<RedisNode> nodes = Collections.singletonList(new RedisNode("10.0.0.100", 26379));
//        nodes.add(new RedisNode("10.0.0.101", 26379));
//        nodes.add(new RedisNode("10.0.0.102", 26379));
//        log.info("nodes.size:{}", nodes.size());
//
//        template = new RedisTemplate<>();
//        // 单机模式
//        JedisConnectionFactory fac = new JedisConnectionFactory(rsc);
//        // 集群模式
//        // JedisConnectionFactory fac = new JedisConnectionFactory(rcc);
//
//        fac.afterPropertiesSet();
//        template.setConnectionFactory(fac);
//        template.setDefaultSerializer(new StringRedisSerializer());
//        template.afterPropertiesSet();

        // LettuceClientConfiguration 客户端配置接口，以下三个实现
        // 单机 RedisStandaloneConfiguration
        // 哨兵 RedisSentinelConfiguration
        // 集群 RedisClusterConfiguration

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("10.0.0.100");
        configuration.setPort(6380);
        configuration.setPassword("PasswordABCD");

        LettuceConnectionFactory lcf = new LettuceConnectionFactory(configuration);
        lcf.afterPropertiesSet();
        template = new RedisTemplate<>();
        template.setConnectionFactory(lcf);
        template.setDefaultSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        log.info("RedisTemplate init");
    }

    @DisplayName("key value操作")
    @Test
    @Order(1)
    public void valueOps() {
        ValueOperations<String, String> ops = template.opsForValue();
        String value = ops.get("kv");
        log.info("kv is {}", value);

        value = ops.getAndSet("kv", DateUtil.now());
        log.info("kv is {} after getAndSet", value);
        value = ops.get("kv");
        log.info("kv is {} after get", value);
    }

    @DisplayName("Hash操作")
    @Test
    @Order(1)
    public void valueHash() {
        HashOperations<String, String, String> ops = template.opsForHash();
        String id = ops.get("SomeOne", "id");
        String name = ops.get("SomeOne", "name");
        log.info("Hash[SomeOne] id={},name={}", id, name);

        ops.put("SomeOne", "id", "1");
        ops.put("SomeOne", "name", DateUtil.now());

        template.expire("SomeOne", 10, TimeUnit.SECONDS);

        id = ops.get("SomeOne", "id");
        name = ops.get("SomeOne", "name");
        log.info("Hash[SomeOne] id={},name={} after set", id, name);
        log.info("Hash[SomeOne] keys:{}", ops.keys("SomeOne"));
        log.info("Hash[SomeOne] hasKey[id] ? {}", ops.hasKey("SomeOne", "id"));
        log.info("Hash[SomeOne] hasKey[id2] ? {}", ops.hasKey("SomeOne", "id2"));
    }

    @DisplayName("List操作")
    @Test
    @Order(3)
    public void list() {
        ListOperations<String, String> ops = template.opsForList();
        List<String> list = ops.range("List", 0, ops.size("List"));
        log.info("List[List] is {}", list);

        if (!list.isEmpty()) {
            String value = ops.rightPop("List");
            log.info("List[List] rightPop is {}", value);
        }

        list = Lists.newArrayList();
        list.add(DateUtil.now() + "_01");
        list.add(DateUtil.now() + "_02");
        list.add(DateUtil.now() + "_03");
        ops.leftPushAll("List", list);

        list = ops.range("List", 0, ops.size("List"));
        log.info("List[List] is {}", list);
    }

    @DisplayName("Set操作")
    @Test
    @Order(4)
    public void set() {
        SetOperations<String, String> ops = template.opsForSet();
        Set<String> set = ops.members("Set");
        log.info("Set[Set] is {}", set);
        ops.add("Set", "A");
        ops.add("Set", "D");
        ops.add("Set", "AA");
        ops.add("Set", "B");
        ops.add("Set", "BB");

        set = ops.members("Set");
        log.info("Set[Set] is {} after add", set);

        // ZSet 排序Set
        ZSetOperations<String, String> opsZ = template.opsForZSet();
        set = opsZ.range("ZSet", 0, opsZ.size("ZSet"));
        log.info("ZSet[ZSet] is {}", set);
        opsZ.add("ZSet", "A", 0);
        opsZ.add("ZSet", "D", 4);
        opsZ.add("ZSet", "AA", 1);
        opsZ.add("ZSet", "B", 1);
        opsZ.add("ZSet", "BB", 2);


        set = opsZ.range("ZSet", 0, opsZ.size("ZSet"));
        log.info("ZSet[ZSet] is {}", set);
    }

}
