package nohi.redis;


import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.SerializationCodec;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>测试Redis with redisson</p>
 * @date 2025/01/16 21:03
 **/
@Slf4j
public class TestRedisson {
    static RedissonClient redisson;

    @BeforeAll
    public static void init() {
        Config config = new Config();
//        config.setCodec(new StringCodec());
        config.setCodec(new SerializationCodec());
        config.useSingleServer()
                .setPassword("123456")
                .setAddress("redis://127.0.0.1:6379");

        redisson = Redisson.create(config);
        log.info("Redisson created");
    }

    @DisplayName("字符串测试")
    @Test
    public void testString() {
        RBucket<String> rBucket = redisson.getBucket("a");
        String aValue = rBucket.get();
        log.info("a value is {}", aValue);
        // 获取后设置值，并设置key2分钟过期
        aValue = rBucket.getAndSet(DateUtil.now(), 2, TimeUnit.MINUTES);
        log.info("a value is {}", aValue);
        aValue = rBucket.get();
        log.info("get a value is {}", aValue);
    }

    @DisplayName("字符串测试")
    @Test
    public void testHash() {
        RMap<String, String> rBucket = redisson.getMap("hashmap");

    }

}

