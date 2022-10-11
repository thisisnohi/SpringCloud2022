package nohi.java.file;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>文件流</p>
 * @date 2022/09/27 14:59
 **/
public class TestFileIO {

    /**
     * 文件输出
     */
    @Test
    public void testFileOutput() {
        String path = "/Users/nohi/tmp/java/1.txt";
        String msg = "this is test";

        try (FileOutputStream fio = new FileOutputStream(path)) {
            fio.write(msg.getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            e.printStackTrace();
        }
        path = "/Users/nohi/tmp/java/2.txt";
        try (FileWriter fw = new FileWriter(path)) {
            fw.write(msg + msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
