package nohi._java._thread;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>ThreadPoolExecutor</p>
 * @date 2022/10/08 18:21
 **/
public class TestThreadPoolExecutor {


    /**
     * 测试线程池，拒绝策略-异常抛出
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testThreadPoolAbortPolicy() throws ExecutionException, InterruptedException {
        // 核心线程数
        int corePoolSize = 2;
        // 最大线程数
        int maximumPoolSize = 2;
        // 测试任务数
        int taskSize = 20;

        ExecutorService executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),  // 指定队列大小
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        ;
        List<Future<String>> futureList = new ArrayList<>();
        long start = System.currentTimeMillis();
        System.out.println("===============================");
        for (int i = 0; i < taskSize; i++) {
            System.out.println("task:" + i);
            try {
                futureList.add(executor.submit(new TestCallable(i, 2)));
            } catch (RejectedExecutionException e) {
                System.err.println("服务拒绝:" + e.getMessage());
            } catch (Exception e) {
                System.out.println("task:" + i + " 执行异常:" + e.getMessage());
            }
        }
        System.out.println("任务创建结束,耗时[" + (System.currentTimeMillis() - start) + "]");
        System.out.println("===============================");
        for (Future<String> stringFuture : futureList) {
            System.out.println("future.get():" + stringFuture.get());
        }
    }

    /**
     * 测试线程池，拒绝策略-使用主线程
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testThreadPoolCallerRunsPolicy() throws ExecutionException, InterruptedException {
        // 核心线程数
        int corePoolSize = 2;
        // 最大线程数
        int maximumPoolSize = 2;
        // 测试任务数
        int taskSize = 20;

        ExecutorService executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),  // 指定队列大小
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        ;
        List<Future<String>> futureList = new ArrayList<>();
        long start = System.currentTimeMillis();
        System.out.println("===============================");
        for (int i = 0; i < taskSize; i++) {
            System.out.println("task:" + i);
            try {
                futureList.add(executor.submit(new TestCallable(i, 2)));
            } catch (Exception e) {
                System.out.println("task:" + i + " 执行异常:" + e.getMessage());
            }
        }
        System.out.println("任务创建结束,耗时[" + (System.currentTimeMillis() - start) + "]");
        System.out.println("===============================");
        for (Future<String> stringFuture : futureList) {
            System.out.println("future.get():" + stringFuture.get());
        }
    }

}
