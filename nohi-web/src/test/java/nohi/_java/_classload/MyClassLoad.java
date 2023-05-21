package nohi._java._classload;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

/**
 * @author NOHI
 * 2022-07-29 16:13
 **/
public class MyClassLoad extends ClassLoader {

    /**
     * 自定义类加载器
     * 继承classLoader  重写findClass()方法
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("F:\\test", name.replace(".", "\\").concat(".class"));
        try {
            FileInputStream inputStream = new FileInputStream(file);
            //FileOutputStream fileoutput = new FileOutputStream(new File("F:/test",name.replace(".","/").concat(".class")));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int by = 0;
            while ((by = inputStream.read()) != 0) {
                outputStream.write(by);
            }

            byte[] bytes = outputStream.toByteArray();
            outputStream.close();

            defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    @Test
    public void showLoaderDir() {

        System.out.println("sun.boot.class.path:" + System.getProperty("sun.boot.class.path"));
        System.out.println("java.ext.dirs:" + System.getProperty("java.ext.dirs"));
        System.out.println("java.class.path:" + System.getProperty("java.class.path"));

        System.out.println("============================================================================");
        URL[] urls = null; //sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urls) {
            System.out.println(url.toExternalForm());
        }

        ClassLoader classLoader = MyClassLoad.class.getClassLoader();
        System.out.println("classLoader:" + classLoader);
        System.out.println("============================================================================");
        String extDir = System.getProperty("java.ext.dirs");
        for (String path : extDir.split(":")) {
            System.out.println(path);
        }
    }

    public static void main(String[] args) {
        try {
            //使用自定义 classLoader
            ClassLoader classLoader = new MyClassLoad();
            //加载指定目录下的类文件
            Class<?> aClass = classLoader.loadClass("cn.bdqn.userconsumer.HelloJol");
//            HelloJol helloJol = (HelloJol) aClass.newInstance();
//            helloJol.Str();
            System.out.println(classLoader.getClass().getClassLoader());
            System.out.println(classLoader.getParent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

