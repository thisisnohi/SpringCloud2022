package nohi.demo.tdd;

/**
 * <h3>SpringCloud2022</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/05/17 12:29
 **/
public class FizzBuzz {

//    /**
//     * FizzBuzz
//     * 可以被3整除，打印Fizz
//     * 可以被5整除，打印Buzz
//     * 可以被3和5同时整除，打印FizzBuzz
//     * 其他打印相应数据
//     *
//     * @param i 入参
//     * @return 返回
//     */
//    public static String compute(int i) {
//        if (i % 3 == 0 && i % 5 == 0) {
//            return "FizzBuzz";
//        }
//
//        if (i % 3 == 0) {
//            return "Fizz";
//        }
//        if (i % 5 == 0) {
//            return "Buzz";
//        }
//
//        return "" + i;
//    }

    /**
     * FizzBuzz
     * 可以被3整除，打印Fizz
     * 可以被5整除，打印Buzz
     * 可以被3和5同时整除，打印FizzBuzz
     * 其他打印相应数据
     *
     * @param i 入参
     * @return 返回
     */
    public static String compute(int i) {
        StringBuffer sb = new StringBuffer();
        if (i % 3 == 0) {
            sb.append("Fizz");
        }
        if (i % 5 == 0) {
            sb.append("Buzz");
        }
        if (sb.isEmpty()) {
            sb.append(i);
        }
        return sb.toString();
    }
}
