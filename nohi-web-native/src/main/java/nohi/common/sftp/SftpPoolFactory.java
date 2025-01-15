package nohi.common.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nohi.common.config.SftpProperties;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.Properties;

/**
 * <h3>nohi-web-native</h3>
 * Sftp线程池工厂
 *
 * @author NOHI
 * @description <p>SftpFactory</p>
 * @date 2023/07/28 21:02
 **/
@Slf4j
@Data
public class SftpPoolFactory extends BasePooledObjectFactory<ChannelSftp> {
    private SftpProperties properties;

    public SftpPoolFactory(SftpProperties properties) {
        this.properties = properties;
    }

    /**
     * 创建连接
     *
     * @return 连接
     */
    @Override
    public ChannelSftp create() {
        try {
            JSch jsch = new JSch();
            Session sshSession = jsch.getSession(properties.getUsername(), properties.getHost(), properties.getPort());
            sshSession.setPassword(properties.getPassword());
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            ChannelSftp channel = (ChannelSftp) sshSession.openChannel("sftp");
            channel.connect();
            return channel;
        } catch (JSchException e) {
            throw new RuntimeException("连接sftp失败", e);
        }
    }

    @Override
    public PooledObject<ChannelSftp> wrap(ChannelSftp channelSftp) {
        return new DefaultPooledObject<>(channelSftp);
    }

    /**
     * 销毁对象
     *
     * @param p 连接池
     */
    @Override
    public void destroyObject(PooledObject<ChannelSftp> p) {
        ChannelSftp channelSftp = p.getObject();
        channelSftp.disconnect();
    }

}
