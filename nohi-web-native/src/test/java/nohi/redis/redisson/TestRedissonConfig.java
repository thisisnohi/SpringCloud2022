package nohi.redis.redisson;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

import java.io.IOException;

/**
 * Redisson配置
 */
@Slf4j
public class TestRedissonConfig {

    /**
     * 程序化配置
     */
    @Test
    public void codeConfig() {
        Config config = new Config();
        // TransportMode.EPOLL 只能在Linux下使用
        config.setTransportMode(TransportMode.NIO);
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
        RedissonClient redisson = Redisson.create(config);
        RBucket<Object> obj = redisson.getBucket("bucket");
        obj.set("this is bucket");
        log.debug("obj:{}", obj);
        log.debug("obj:{}", obj.get());
    }

    @Test
    public void fileConfig() throws IOException {
        Config config = Config.fromJSON("configFile.json");
        Config.fromYAML("xxx.yml");
    }

    /**
     * 集群模式配置
     */
    @Test
    public void clusterConfig() {
        Config config = new Config();
        // TransportMode.EPOLL 只能在Linux下使用
        config.setTransportMode(TransportMode.NIO);
        // 配置哨兵模式连接,address为哨兵端口
        config.useClusterServers()
                .setScanInterval(2000) // 集群状态扫描间隔时间，单位毫秒
                .setPassword("PasswordABC4") // 授权密码
                .addNodeAddress("redis://10.0.0.100:6380", "redis://10.0.0.100:6381")
                .addNodeAddress("redis://10.0.0.101:6380", "redis://10.0.0.101:6381")

        ;
        RedissonClient redisson = Redisson.create(config);
        RBucket<Object> obj = redisson.getBucket("bucket");
        obj.set("this is bucket");
        log.debug("obj:{}", obj);
        log.debug("obj:{}", obj.get());
    }
}
