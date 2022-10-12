package nohi._java._classload;


import org.junit.jupiter.api.Test;

/**
*
* @author NOHI
* @date 2022/8/12 12:19
*/
public class TestClassLoadStatic {


    /**
     * 测试静态属性方法初始化
     * @param
     * @return
     * @author NOHI
     * @date 2022/8/12 12:38
     */
    @Test
    public void testStaticInit(){
        Son.test();
    }
}
class Son extends Father {
    public static Integer intVal11 = 20;
    static {
        intVal11 = 21;
    }

    public static void test(){
        System.out.println("===========1============");
        System.out.println("intVal1:" + intVal1);
        System.out.println("intVal11:" + intVal11);
        System.out.println("intVal2:" + intVal2);
        System.out.println("finalIntVal:" + finalIntVal);
        System.out.println("===========2============");
    }
}
class Father {
    public static Integer intVal1 = 10;
    public static Integer intVal11 = 11;
    public static final Integer finalIntVal = 10;
    static {
        intVal1 = 11;
        intVal11 = 12;
        intVal2 = 11;
    }
    public static Integer intVal2 = 10;

    public static void test(){
        System.out.println("==========F1=============");
        System.out.println("intVal1:" + intVal1);
        System.out.println("intVal2:" + intVal2);
        System.out.println("finalIntVal:" + finalIntVal);
        System.out.println("==========F2=============");
    }
}
