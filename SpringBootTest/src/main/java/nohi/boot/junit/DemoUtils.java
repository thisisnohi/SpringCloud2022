package nohi.boot.junit;


import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p>单元测试</p>
 * @date 2023/01/10 21:43
 **/
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class DemoUtils {
    List list1 = List.of("A", "B", "C");
    String[] stringArray = {"A", "B", "C"};

    /**
     * 检查对象是否为空
     *
     * @param obj
     * @return
     */
    public Object checkNull(Object obj) {
        if (obj != null) {
            return obj;
        }
        return null;
    }

    public int intAdd(int a, int b) {
        return add(a, b);
    }

    private int add(int a, int b) {
        return a + b;
    }

    /**
     * 无引用私有方法
     *
     * @return
     */
    private int unlinkMethod() {
        return 0;
    }

    public int intSub(int a, int b) {
        return a - b;
    }

    public int intMult(int a, int b) {
        return a * b;
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 返回List
     *
     * @return
     */
    public List<String> getList() {
        return list1;
    }

    /**
     * 返回字符串数组
     *
     * @return
     */
    public String[] getStrArray() {
        return stringArray;
    }

    public void throwException(int i) throws Exception {
        if (i < 0) {
            throw new Exception("i < 0 throw Excepton");
        }
    }

    /**
     * test assertTimeout
     * @param sleepSecond
     * @throws InterruptedException
     */
    public void sleep(int sleepSecond) throws InterruptedException {
        log.info("I am going to sleep {}s", sleepSecond);
        TimeUnit.SECONDS.sleep(sleepSecond);
        log.info("Sleeping over");

    }
}
