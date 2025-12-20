package nohi.demo.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.List;

/**
 * 文件工具类
 *
 * @author NOHI
 * @date 2023/8/20 09:49
 */
public class FileUtils {
    public static String docDir = "";

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static void clearPath(String path) {
        if (StringUtils.isNotBlank(path)) {
            try {
                File file = new File(path);
                if (file.exists() && file.isDirectory()) {
                    deleteDir(file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断路径是否存在
     *
     * @param path
     * @return
     */
    public static boolean exists(String path) {
        if (StringUtils.isBlank(path)) {

            return false;
        }
        File file = new File(path);
        return file.exists();
    }

    /**
     * 创建目录
     *
     * @param path
     * @return
     */
    public static void createDir(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new Exception("创建文件目录[" + path + "]失败!");
            }
        }
    }

    /**
     * 保存输入流中的数据至文件，指定文件名
     * 默认UTF-8
     *
     * @param docPath
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String storeMessage(InputStream is, String docPath, String fileName) throws Exception {
        //创建目录
        createDir(docPath);

        File file = new File(docPath + File.separator + fileName);
        if (file.exists()) {
            throw new Exception("文件已经存在");
        }
        if (null == is) {
            throw new Exception("输入流为空!");
        }

        try (FileOutputStream fos = new FileOutputStream(file);) {
            byte[] bytes = new byte[2048];
            int length = -1;
            while ((length = is.read(bytes)) > -1) {
                fos.write(bytes, 0, length);
            }
            fos.flush();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
        //返回文件路径和文件名
        return docPath + File.separator + fileName;
    }

    /**
     * 保存信息至文件，指定文件名
     * 默认UTF-8
     *
     * @param message
     * @param docPath
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String storeMessage(String message, String docPath, String fileName) throws Exception {
        return storeMessage(message, "UTF-8", docPath, fileName, false);
    }

    /**
     * 保存信息至文件，指定文件名
     * 默认UTF-8
     *
     * @param message
     * @param docPath
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String storeMessage(String message, String docPath, String fileName, boolean checkExists) throws Exception {
        return storeMessage(message, "UTF-8", docPath, fileName, checkExists);
    }

    /**
     * 保存信息至文件，指定文件名、字符集
     *
     * @param message
     * @param encode
     * @param docPath
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String storeMessage(String message, String encode, String docPath, String fileName, boolean checkExists) throws Exception {
        //创建目录
        createDir(docPath);

        File file = new File(docPath + File.separator + fileName);
        // 如果文件存在: 文件是目录/需要检查文件是否存在,则报错
        //noinspection AlibabaAvoidComplexCondition
        if (file.exists() && (file.isDirectory() || checkExists)) {
            throw new Exception("文件已经存在");
        }

        if (null == message) {
            message = "";
        }

        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter writer = new OutputStreamWriter(fos, encode);) {
            writer.write(message);
            writer.flush();
        } catch (Exception e) {
            throw e;
        }

        return docPath + File.separator + fileName;
    }

    /**
     * 保存信息至文件，指定文件名、字符集
     *
     * @param message
     * @param encode
     * @param docPath
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String storeMessageAppend(String message, String encode, String docPath, String fileName) throws Exception {
        // 创建目录
        createDir(docPath);

        File file = new File(docPath + File.separator + fileName);
        // 如果文件存在: 文件是目录/需要检查文件是否存在,则报错
        if (file.exists() && file.isDirectory()) {
            throw new Exception("文件已经存在，且是文件目录");
        }

        if (null == message) {
            message = "";
        }


        try(FileOutputStream fos = new FileOutputStream(file, true);
            OutputStreamWriter writer = new OutputStreamWriter(fos, encode);
        ) {
            writer.write(message);
            writer.flush();
        } catch (Exception e) {
           throw e;
        }

        return docPath + File.separator + fileName;
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return 返回
     * @throws Exception 异常
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static String readStringfromStream(InputStream inStream) throws Exception {
        byte[] buffer = readStream(inStream);
        if (null != buffer) {
            return new String(buffer, "UTF-8");
        }
        return "";
    }

    /**
     * 从输入流中获取数据
     */
    public static String readStringfromPath(String path) throws Exception {
        return readStringfromStream(new FileInputStream(path));
    }

    /**
     * 获取文件扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 递归获取指定目录下的所有version.json文件的内容.
     *
     * @param path
     * @param list
     * @return
     * @throws Exception
     */
    public static List<String> getAllVersionInfo(File path, List<String> list) throws Exception {
        File[] files = path.listFiles();
        for (File fs : files) {
            if (fs.isDirectory()) {
                getAllVersionInfo(fs, list);
            } else {
                if (fs.getAbsolutePath().endsWith("version.json")) {
                    list.add(readStringfromPath(fs.getAbsolutePath()));
                }
            }
        }
        return list;
    }

    /**
     * 拼接路径
     *
     * @param path
     * @param appendPath
     * @return
     */
    public static String appendFilePath(String path, String... appendPath) {
        if (null == path) {
            return null;
        }
        path = path.replaceAll("\\\\", "/");
        StringBuilder sb = new StringBuilder(path);
        if (null != appendPath && appendPath.length > 0) {
            for (int i = 0; i < appendPath.length; i++) {
                if (!sb.toString().endsWith("/")) {
                    sb.append("/");
                }
                sb.append(appendPath[i]);
            }

        }
        return sb.toString();
    }
}
