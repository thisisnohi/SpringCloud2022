package nohi._java._try;

import org.junit.jupiter.api.Test;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>测试try</p>
 * @date 2022/10/08 17:42
 **/
public class TestTry {

    @Test
    public void testTry(){
        System.out.println("==========");
        String msg = tryMethod("1111");
        System.out.println("返回值-msg:" + msg);
        System.out.println("==========");
    }
    public String tryMethod(String abc){
        System.out.println("1:" + abc);
        try {
            System.out.println("try todo");
            return "1";
        } catch (Exception e) {
            System.out.println("catch:" + e.getMessage());
        } finally {
            System.out.println("finally");
            return "2";
        }
//        return "null";
    }
}
