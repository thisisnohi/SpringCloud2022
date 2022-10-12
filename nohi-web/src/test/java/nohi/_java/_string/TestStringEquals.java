package nohi._java._string;


import org.junit.jupiter.api.Test;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>测试string == equals</p>
 * @date 2022/10/08 17:15
 **/
public class TestStringEquals {

    @Test
    public void testFinal() {
        final String a = "11";
        final String b = "11";
        System.out.println("final a==b -> " + (a == b));
        System.out.println("final a equals b -> " + (a.equals(b)));
    }

    @Test
    public void testNewString(){
        String a = "11";
        String b = "11";
        System.out.println("String a==b -> " + (a == b));
        System.out.println("String a equals b -> " + (a.equals(b)));

        a = new String("11");
        b = new String("11");
        System.out.println("new String a==b -> " + (a == b));
        System.out.println("new String a equals b -> " + (a.equals(b)));

        a = String.valueOf("11");
        b = String.valueOf("11");
        System.out.println("String.valueOf a==b -> " + (a == b));
        System.out.println("String.valueOf a equals b -> " + (a.equals(b)));

    }
}
