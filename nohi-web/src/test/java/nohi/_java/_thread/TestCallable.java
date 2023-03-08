package nohi._java._thread;

import java.security.SecureRandom;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2022/10/19 11:06
 **/
public class TestCallable implements Callable<String> {
    private static SecureRandom random = new SecureRandom();
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

        title = "[" + Thread.currentThread().getName() + "]\t" + title + "\tsleep\t" + sleep + "s";
        int i = 0;
        while (i++ < 5) {
            System.out.println(title + "\trun\t" + i);
            TimeUnit.SECONDS.sleep(sleep);
        }
        System.out.println(title + "\tover");
        return title;
    }
}
