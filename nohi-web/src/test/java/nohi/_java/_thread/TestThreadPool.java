package nohi._java._thread;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2022/10/08 18:21
 **/
public class TestThreadPool {
    private static SecureRandom random = new SecureRandom();

    @Test
    public void testFixedPool() throws ExecutionException, InterruptedException {
        int poolInit = 2;
        int poolMax = 2;
        int taskSize = 20;
        // 固定大小
        ExecutorService executor = Executors.newFixedThreadPool(poolMax);
        List<Future<String>> futureList = new ArrayList<>();
        long start = System.currentTimeMillis();
        System.out.println("===============================");
        for (int i = 0; i < taskSize; i++) {
            System.out.println("task:" + i);
            futureList.add(executor.submit(new TestCallable(i, 2)));
        }
        System.out.println("任务创建结束,耗时[" + (System.currentTimeMillis() - start) + "]");
        System.out.println("===============================");
        for (Future<String> stringFuture : futureList) {
            System.out.println("future.get():" + stringFuture.get());
        }
    }

    @Test
    public void testCachedPool() throws ExecutionException, InterruptedException {
        int poolInit = 2;
        int poolMax = 2;
        int taskSize = 20;
        // 固定大小
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<String>> futureList = new ArrayList<>();
        long start = System.currentTimeMillis();
        System.out.println("===============================");
        for (int i = 0; i < taskSize; i++) {
            System.out.println("task:" + i);
            futureList.add(executor.submit(new TestCallable(i, 20)));
        }

        System.out.println("任务创建结束,耗时[" + (System.currentTimeMillis() - start) + "]");
        System.out.println("===============================");
        for (Future<String> stringFuture : futureList) {
            System.out.println("future.get():" + stringFuture.get());
        }
        System.out.println("任务创建结束,耗时[" + (System.currentTimeMillis() - start) + "]");
    }

    @Test
    public void testScheduledPool() throws ExecutionException, InterruptedException {
        int poolInit = 2;
        int poolMax = 2;
        int taskSize = 20;
        // 固定大小
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        List<Future<String>> futureList = new ArrayList<>();
        long start = System.currentTimeMillis();
        System.out.println("===============================");
        for (int i = 0; i < taskSize; i++) {
            System.out.println("task:" + i);
            int finalI = i;
            ScheduledFuture future = executor.scheduleAtFixedRate(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    long sleep = 5;
                    String title ="TASK\5" + finalI + "\tsleep\t" + sleep + "s";
                    int i = 0;
                    while (i++ < 5) {
                        System.out.println(title + "\trun\t" + i);
                        TimeUnit.SECONDS.sleep(sleep);
                    }
                    System.out.println(title + "\tover");
                }
            }, 0, 3, TimeUnit.MILLISECONDS);
            futureList.add(future);
        }

        System.out.println("任务创建结束,耗时[" + (System.currentTimeMillis() - start) + "]");
        System.out.println("===============================");
        for (Future<String> stringFuture : futureList) {
            System.out.println("future.get():" + stringFuture.get());
        }
        System.out.println("任务创建结束,耗时[" + (System.currentTimeMillis() - start) + "]");
    }

    static class TestCallable implements Callable<String> {
        private String title;
        // 固定sleep时间
        Integer fixedSleep;

        public TestCallable(int i, Integer fixedSleep) {
            title = "TestCallable\t" + i;
            this.fixedSleep = fixedSleep;
        }

        public TestCallable(int i) {
            title = "TestCallable\t" + i;
        }

        @Override
        public String call() throws Exception {
            long sleep = 0;
            if (null == fixedSleep) {
                sleep = random.nextInt(15);
            } else {
                sleep = fixedSleep;
            }

            title = title + "\tsleep\t" + sleep + "s";
            int i = 0;
            while (i++ < 5) {
                System.out.println(title + "\trun\t" + i);
                TimeUnit.SECONDS.sleep(sleep);
            }
            System.out.println(title + "\tover");
            return title;
        }
    }

}
