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
 * @description <p>Executors 方式创建线程池，不推荐使用，如果大量请求会出现OOM</p>
 * @date 2022/10/08 18:21
 **/
public class TestExecutors {
    private static SecureRandom random = new SecureRandom();

    @Test
    public void testFixedPool() throws ExecutionException, InterruptedException {
        int poolInit = 2;
        int poolMax = 2;
        int taskSize = 20;

        // 固定大小
        /* 该方法返回一个固定线程数量的线程池，该线程池池中的线程数量始终不变。
         * 当有一个新的任务提交时，线程池中若有空闲线程，则立即执行。
         * 若没有，则新的任务会被暂存在一个任务队列中，待有线程空闲时，便处理在任务队列中的任务
         * 默认等待队列长度为Integer.MAX_VALUE
         */
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
    public void testSinglePool() throws ExecutionException, InterruptedException {
        int poolInit = 2;
        int poolMax = 2;
        int taskSize = 20;

        // 固定大小
        /* 该方法返回一个只有一个线程的线程池。
         * 若多余一个任务被提交到线程池，任务会被保存在一个任务队列中，等待线程空闲，按先入先出顺序执行队列中的任务
         * 默认等待队列长度为Integer.MAX_VALUE
         */
        ExecutorService executor = Executors.newSingleThreadExecutor();
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
        System.out.println("任务创建结束,耗时[" + (System.currentTimeMillis() - start) + "]");
    }

    @Test
    public void testCachedPool() throws ExecutionException, InterruptedException {
        int poolInit = 2;
        int poolMax = 2;
        int taskSize = 20;
        /*
         * 该方法返回一个可根据实际情况调整线程数量的线程池。
         * 线程池的线程数量不确定，但若有空闲线程可以复用，则会优先使用可复用的线程。
         * 若所有线程均在工作，又有新任务的提交，则会创建新的线程处理任务。
         * 所有线程在当前任务执行完毕后，将返回线程池进行复用
         */
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
        /* 该方法返回一个ScheduledExecutorService对象。
         * ScheduledExecutorService接口在ExecutorService接口之上扩展了在给定时间内执行某任务的功能，
         * 如在某个固定的延时之后执行，或者周期性执行某个任务
         *
         * newScheduledThreadPool 线程池大小为1
         */
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
                    String title = "TASK\5" + finalI + "\tsleep\t" + sleep + "s";
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
