package nohi.common.sftp;

import com.google.common.collect.Maps;
import com.jcraft.jsch.ChannelSftp;
import lombok.extern.slf4j.Slf4j;
import nohi.common.config.SftpProperties;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>SFTPTemplate</p>
 * @date 2023/07/28 21:08
 **/
@Slf4j
public class SftpTemplate {
    Set<String> fileSuffixSet = Stream.of(".txt", ".zip").collect(Collectors.toSet());

    Map<String, SftpPool> sftpPoolMap = Maps.newHashMap();

    public String getKey(SftpProperties sftpProperties) {
        if (null == sftpProperties) {
            throw new RuntimeException("sftp配置为空");
        }
        return sftpProperties.getUsername() + "@" + sftpProperties.getHost() + ":" + sftpProperties.getPort();
    }

    /**
     * 创建线程池
     *
     * @param sftpProperties
     * @return
     */
    public SftpPool createPool(SftpProperties sftpProperties) {
        String key = getKey(sftpProperties);
        log.info("线程池[{}]", key);
        if (!sftpPoolMap.containsKey(key)) {
            SftpPoolFactory factory = new SftpPoolFactory(sftpProperties);
            SftpPool pool = new SftpPool(factory);
            sftpPoolMap.put(key, pool);
        }
        return sftpPoolMap.get(key);
    }
}
