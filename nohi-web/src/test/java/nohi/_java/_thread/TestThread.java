package nohi._java._thread;



import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>测试Thread</p>
 * @date 2022/10/08 18:05
 **/
public class TestThread {

    @Test
    public void testRunnable() {
        //1)lambda表达式形式传递给线程构造器
        Runnable runnable1 = () -> {
            System.out.println("我是使用lambda表达式实现的Runnable对象实现 version1");
        };
        new Thread(runnable1).start();
        new Thread(() -> {
            System.out.println("我是使用lambda表达式实现的Runnable对象实现 version2");
        }).start();
    }

    @Test
    public void testCallable() {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(6000);
                return new Random().nextInt();
            }
        };
        FutureTask<Integer> future = new FutureTask<>(callable);
        new Thread(future).start();
        try {
            Thread.sleep(1000);
            System.out.println("hello begin");
            System.out.println("future.isDone():" + future.isDone());
            System.out.println("future.get():" + future.get());
            System.out.println("future.isDone():" + future.isDone());
            System.out.println("hello end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
