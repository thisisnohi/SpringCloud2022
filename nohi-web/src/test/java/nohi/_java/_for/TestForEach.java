package nohi._java._for;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NOHI
 * 2022-07-14 13:45
 **/
public class TestForEach {

    /**
     * for each 中断
     */
    @Test
    public void testForEachBreak(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        list.forEach(item -> {
            System.out.println("item:" + item);
            if ("2".equalsIgnoreCase(item)) {
                System.out.println("======2==== break;");
//                break;
//                continue;
                return;
            }
        });

    }
}
