package nohi.boot.junit;

import java.util.UUID;

/**
 * <h3>SpringBootTest</h3>
 *
 * @author NOHI
 * @description <p>单元测试</p>
 * @date 2023/01/10 21:43
 **/
public class DemoUtils {

    public int intAdd(int a, int b){
        return add(a, b);
    }

    private int add(int a, int b){
        return a + b;
    }

    /**
     * 无引用私有方法
     * @return
     */
    private int unlinkMethod(){
        return 0;
    }

    public int intSub(int a, int b){
        return a - b;
    }

    public int intMult(int a, int b){
        return a * b;
    }

    public static String uuid(){
        return UUID.randomUUID().toString();
    }
}
