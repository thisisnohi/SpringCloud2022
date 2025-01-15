package nohi.common.sftp;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>SftpPool</p>
 * @date 2023/07/28 21:04
 **/

import com.jcraft.jsch.ChannelSftp;
import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * Sftp线程池
 * @author NOHI
 * @date 2023/8/20 09:58
 */
@Data
public class SftpPool {

    private GenericObjectPool<ChannelSftp> pool;

    public SftpPool(SftpPoolFactory factory) {
        this.pool = new GenericObjectPool<>(factory, factory.getProperties().getPool());
    }

    /**
     * 获取一个sftp连接对象
     *
     * @return sftp连接对象
     */
    public ChannelSftp borrowObject() {
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException("获取sftp连接失败", e);
        }
    }

    /**
     * 归还一个sftp连接对象
     *
     * @param channelSftp sftp连接对象
     */
    public void returnObject(ChannelSftp channelSftp) {
        if (channelSftp != null) {
            pool.returnObject(channelSftp);
        }
    }

}
