package nohi.test.thread;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <h3>SpringCloud2022</h3>
 *
 * @author NOHI
 * @description <p>测试线程安全</p>
 * @date 2025/01/15 21:31
 **/
@Slf4j
public class TestThreadSafe {

    private int count = 0;

    /**
     * 测试线程安全，多个线程修改变量
     */
    @Test
    public void testAdd() throws InterruptedException {

        count = 0;
        int total = 20000;

        long start = System.currentTimeMillis();
        // 线程计数器
        CountDownLatch cd = new CountDownLatch(total);
        for (int i = 0; i < total; i++) {
            new Thread(() -> {
                try {
                    // 增加了sleep很容易出现，最后的count值不等于total
                    // 因为增加了线程调度时间，结果不确定的概率增加了
                    // TimeUnit.MICROSECONDS.sleep(RandomUtils.nextInt(10, 1000));
                    this.count++;
                } catch (Exception e) {
                    log.error("异常:{}", e.getMessage());
                } finally {
                    cd.countDown();
                }
            }).start();
        }
        // 等待所有线程执行完
        cd.await();
        log.info("add over. count={}, 耗时:{}", count, System.currentTimeMillis() - start);

    }

}
