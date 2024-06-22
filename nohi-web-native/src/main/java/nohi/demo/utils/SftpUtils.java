package nohi.demo.utils;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

/**
 * STFP工具类
 * @author NOHI
 * @date 2023/8/20 09:58
 */
@Slf4j
public class SftpUtils {
    public static final String POINT = ".";
    public static final String DB_POINT = "..";

    public static ChannelSftp getChanel(String hostName, int port, String userName, String password) throws Exception {
        Channel channel;
        Session sshSession;
        /** 配置连接信息 **/
        JSch jSch = new JSch();
        log.debug("before getSession");
        sshSession = jSch.getSession(userName, hostName, port);
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshConfig.put("kex", "diffie-hellman-group1-sha1");
        sshSession.setConfig("StrictHostKeyChecking","no");
        sshSession.setConfig("kex","diffie-hellman-group1-sha1");
        sshSession.setConfig(sshConfig);
        /** 连接 **/
        log.debug("before connect");
        sshSession.connect();
        /** 打开SFTP通道 **/
        log.debug("before openChannel sftp");
        channel = sshSession.openChannel("sftp");
        channel.connect();
        log.debug("channel connect");
        return (ChannelSftp) channel;
    }

    /**
     * 上传文件
     *
     * @param sftp      sftp通道
     * @param localDir  本地目录
     * @param remoteDir 远程目录
     * @param fileName  文件名
     * @return 是否成功
     */
    public static boolean uploadFile(ChannelSftp sftp, String localDir, String remoteDir, String fileName) throws FileNotFoundException, SftpException {
        long start = System.currentTimeMillis();

        boolean flag = false;
        InputStream inputStream = null;
        File file = new File(localDir);
        if (!file.exists()) {
            // 文件夹不存在 创建文件夹
            log.error("目录[" + localDir + "]不存在");
            throw new RuntimeException("文件[" + localDir + "]不存在");
        }
        String localFile = localDir + "/" + fileName;
        log.debug("localFile:{}", localFile);
        File inputFile = new File(localFile);
        if (!inputFile.exists()) {
            log.error("文件[" + localFile + "]不存在");
            throw new RuntimeException("文件[" + localFile + "]不存在");
        }

        try {
            // 判断路径是否存在,不存在创建目录
            cdPath(sftp, remoteDir, Boolean.TRUE);

            inputStream = new FileInputStream(inputFile);
            sftp.put(inputStream, fileName);

            flag = true;
            log.debug("文件[{}]传输成功", localFile);
        } catch (Exception e) {
            log.error("FTP传送文件失败:{}", e.getMessage());
            throw e;
        } finally {
            closeChannel(sftp);
            IOUtils.closeQuietly(inputStream);
            log.info("文件[{}]传输成功,耗时[{}]", fileName, System.currentTimeMillis() - start);
        }
        return flag;
    }

    /**
     * 进入目录
     *
     * @param sftp sftp通道
     * @param path 目录
     * @throws SftpException 异常
     */
    public static void cdPath(ChannelSftp sftp, String path, boolean createPath) throws SftpException {
        if (StringUtils.isBlank(path)) {
            log.error("path is null");
            return;
        }
        path = path.replace("\\", "/");
        try {
            sftp.cd(path);
            log.info("进入目录:{}", path);
        } catch (SftpException e) {
            log.error("进入目录[{}]失败:{}", path, e.getMessage());
            if (!createPath) {
                log.warn("目录不存在，返回");
                throw new RuntimeException("进入目录异常[" + path + "]" + e.getMessage());
            }
            log.info("创建目录[{}]", path);
            String[] dirs = path.split("/");
            String tempPath = "";
            for (String dir : dirs) {
                if (StringUtils.isBlank(dir)) {
                    continue;
                }
                tempPath += "/" + dir;
                try {
                    sftp.cd(tempPath);
                    log.error("cd目录[{}]失败:{},创建目录", tempPath, e.getMessage());
                } catch (SftpException ex) {
                    log.error(ex.getMessage());
                    log.info("创建目录[{}]", tempPath);
                    // 创建目录
                    sftp.mkdir(tempPath);
                    sftp.cd(tempPath);
                }
            }
        }
    }

    public static void closeChannel(Channel channel) {
        try {
            if (null != channel) {
                if (channel.isConnected()) {
                    channel.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭连接
     */
    private static void closeSession(Session session) {
        try {
            if (null != session) {
                if (session.isConnected()) {
                    session.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 扫描目录
     *
     * @param channelSftp sftp通道
     * @param remoteDir   远程目录
     * @return 集合
     */
    public static Vector<ChannelSftp.LsEntry> scanDirAll(ChannelSftp channelSftp, String remoteDir) throws SftpException {
        if (null == channelSftp || !channelSftp.isConnected() || channelSftp.isClosed()) {
            log.error("没有打开或未连接 channelSftp is null or unconnected or is closed");
            throw new RuntimeException("channelSftp 没有打开或未连接");
        }
        /** 扫描目录并返回 **/
        return channelSftp.ls(remoteDir);
    }

    /**
     * 扫描目录, 排除 . ..
     *
     * @param channelSftp sftp通道
     * @param remoteDir   远程目录
     * @return
     */
    public static Vector<ChannelSftp.LsEntry> scanDirWithSelector(ChannelSftp channelSftp, String remoteDir, Set<String> fileSuffixSet) throws SftpException {
        /** 校验参数 **/
        checkChannel(channelSftp);

        Vector<ChannelSftp.LsEntry> vector = new Vector<ChannelSftp.LsEntry>();

        ChannelSftp.LsEntrySelector selector = new ChannelSftp.LsEntrySelector() {
            @Override
            public int select(ChannelSftp.LsEntry lsEntry) {
                String fileName = lsEntry.getFilename();
                // 过滤Linux下 . ..
                if (fileName.equals(POINT) || fileName.equals(DB_POINT)) {
                    return CONTINUE;
                }
                if (lsEntry.getAttrs().isDir() || lsEntry.getAttrs().isLink()) {
                    log.info("文件[{}]是目录或链接，跳转", fileName);
                    return CONTINUE;
                }
                if (fileName.lastIndexOf(POINT) <= 0) {
                    log.info("文件[{}]名没有后缀，跳过", fileName);
                    return CONTINUE;
                }
                //
                String sufix = fileName.substring(fileName.lastIndexOf("."));
                if (!fileSuffixSet.stream().anyMatch(s -> s.equalsIgnoreCase(sufix))) {
                    log.info("文件[{}]后缀[{}]不符合要求，跳过", fileName, sufix);
                    return CONTINUE;
                }
                vector.add(lsEntry);
                return CONTINUE;
            }
        };

        /** 扫描目录并返回 **/
        channelSftp.ls(remoteDir, selector);
        return vector;
    }

    public static void checkChannel(ChannelSftp channelSftp) {
        if (null == channelSftp || !channelSftp.isConnected() || channelSftp.isClosed()) {
            log.error("没有打开或未连接 channelSftp is null or unconnected or is closed");
            throw new RuntimeException("channelSftp 没有打开或未连接");
        }
    }

    /**
     * 下载文件
     *
     * @param channelSftp    sftp连接
     * @param remoteFilePath 远程文件路径
     * @param localFilePath  本地文件路径
     */
    public static void downloadFile(ChannelSftp channelSftp, String remoteFilePath, String localFilePath) throws Exception {
        /** 校验参数 **/
        checkChannel(channelSftp);
        /** 下载 **/
        log.info("下载文件[{}]to local[{}]", remoteFilePath, localFilePath);
        // 如果目录不存在，则创建
        FileUtils.createDir(new File(localFilePath).getParent());
        // 下载文件
        channelSftp.get(remoteFilePath, localFilePath);
    }

    /**
     * 删除文件
     *
     * @param channelSftp    sftp通道
     * @param remoteFilePath 远程目录
     * @throws SftpException 异常
     */
    public static void rmFile(ChannelSftp channelSftp, String remoteFilePath) throws SftpException {
        /** 校验参数 **/
        checkChannel(channelSftp);
        if (StringUtils.isBlank(remoteFilePath)) {
            log.error("文件路径为空[{}]", remoteFilePath);
            throw new RuntimeException("文件路径为空");
        }
        /** 删除文件 **/
        log.info("删除文件[{}]", remoteFilePath);
        channelSftp.rm(remoteFilePath);
    }

}
