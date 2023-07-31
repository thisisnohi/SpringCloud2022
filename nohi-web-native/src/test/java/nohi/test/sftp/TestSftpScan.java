package nohi.test.sftp;

import com.jcraft.jsch.ChannelSftp;
import lombok.extern.slf4j.Slf4j;
import nohi.common.config.SftpProperties;
import nohi.common.sftp.SftpPool;
import nohi.common.sftp.SftpTemplate;
import nohi.demo.utils.FileUtils;
import nohi.demo.utils.SftpUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>sftp扫描</p>
 * @date 2023/07/28 20:16
 **/
@Slf4j
public class TestSftpScan {

    Set<String> fileSuffixSet = Stream.of(".txt", ".zip", ".mp4").collect(Collectors.toSet());


    @Test
    public void testSftpScan() {
        String host = "192.168.1.8";
        int port = 22;
        String username = "test";
        String password = "test";

        String remoteDir = "/home/nohi/sftp/input";
        String localDir = "/Users/nohi/tmp/sftp";

        try {
            ChannelSftp channelSftp = SftpUtils.getChanel(host, port, username, password);

            Vector<ChannelSftp.LsEntry> vector = SftpUtils.scanDirWithSelector(channelSftp, remoteDir, fileSuffixSet);
            log.info("目录[{}]下文件数[{}]", remoteDir, vector.size());
            for (ChannelSftp.LsEntry fileEntry : vector) {
                String fileName = fileEntry.getFilename();
                String remoteFilePath = FileUtils.appendFilePath(remoteDir, fileName);
                String localFilePath = FileUtils.appendFilePath(localDir, fileName);
                // 文件传输
                fileTrans(channelSftp, remoteFilePath, localFilePath);
            }

        } catch (Exception e) {
            log.error("文件处理异常");
        }

    }

    public void fileTrans(ChannelSftp channelSftp, String remoteFilePath, String localFilePath) throws Exception {
        String title = String.format("文件[%s]处理", remoteFilePath);
        log.debug(title);
        // 下载文件
        SftpUtils.downloadFile(channelSftp, remoteFilePath, localFilePath);
        // 删除文件
        SftpUtils.rmFile(channelSftp, remoteFilePath);
    }

    /**
     * 文件传输线程池
     *
     * @return 线程池
     */
    public ThreadPoolExecutor initExecThreadPool() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5, // 核心线程数
                10,   // 最大线程数
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100),  // 指定队列大小
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy() // 默默的丢弃无法处理,等待下次执行
        );
        return executor;
    }

    @Test
    public void testSftpPoolScan() throws InterruptedException {
        String host = "192.168.1.8";
        int port = 22;
        String username = "test";
        String password = "test";

        String remoteDir = "/home/nohi/sftp/input";
        String localDir = "/Users/nohi/tmp/sftp";
        // 服务器配置
        SftpProperties sftpProperties = new SftpProperties();
        sftpProperties.setHost(host);
        sftpProperties.setPort(port);
        sftpProperties.setUsername(username);
        sftpProperties.setPassword(password);
        // 目录配置
        sftpProperties.setRemoteDir(remoteDir);
        sftpProperties.setLocalDir(localDir);

        SftpTemplate sftpTemplate = new SftpTemplate();
        // 创建sftp连接线程池
        SftpPool pool = sftpTemplate.createPool(sftpProperties);
        // 文件传输线程池
        ThreadPoolExecutor executor = initExecThreadPool();

        // 获取sftp通道
        ChannelSftp channelSftp = null;
        channelSftp = pool.borrowObject();
        String title = sftpTemplate.getKey(sftpProperties) + "文件扫描";
        log.info("{} 扫描开始", title);
        while (true) {
            long start = System.currentTimeMillis();
            log.debug("{} start[{}]", title, start);
            try {

                Vector<ChannelSftp.LsEntry> vector = SftpUtils.scanDirWithSelector(channelSftp, remoteDir, fileSuffixSet);
                log.info("目录[{}]下文件数[{}]", remoteDir, vector.size());
                if (vector.size() <= 0) {
                    continue;
                }
                List<CompletableFuture> cfList = new ArrayList<>();
                CompletableFuture[] cfArray = new CompletableFuture[vector.size()];
                vector.stream().forEach(fileEntry -> {
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        ChannelSftp poolsftp = null;
                        String fileName = fileEntry.getFilename();
                        String remoteFilePath = FileUtils.appendFilePath(remoteDir, fileName);
                        String localFilePath = FileUtils.appendFilePath(localDir, fileName);
                        String subTitle = String.format("%s 文件[%s]处理", title, fileName);
                        long fileStart = System.currentTimeMillis();
                        log.info("{} start", subTitle);
                        try {
                            poolsftp = pool.borrowObject();
                            // 文件传输
                            fileTrans(poolsftp, remoteFilePath, localFilePath);
                        } catch (Exception e) {
                            log.error("{} 处理:{}", subTitle, e.getMessage());
                        } finally {
                            long fileEnd = System.currentTimeMillis() - fileStart;
                            log.info("{} end 文件处理耗时[{}]", subTitle, fileEnd);
                            try {
                                pool.returnObject(poolsftp);
                            } catch (Exception e) {
                                log.error("{} 释放sftp通道异常:{}", subTitle, e.getMessage());
                            }
                        }
                    }, executor);
                    cfList.add(future);
                });
                CompletableFuture.allOf(cfList.toArray(cfArray)).join(); // 全执行完
            } catch (Exception e) {
                log.error("文件处理异常");
            } finally {
                long takeTimes = System.currentTimeMillis() - start;
                log.debug("{} end 扫描后文件处理耗时[{}]", title, takeTimes);
                if (takeTimes < 1000) {
                    log.info("{} 耗时小于1000ms, 延迟1000ms后执行", title);
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
            }
        }
        // log.info("{}扫描结束", title);
    }

}
