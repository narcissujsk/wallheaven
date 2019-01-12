package wall.heaven;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class HavenProject {

    /**
     * wallhaven 图片网址格式
     *
     * https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-129624.jpg
     * https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-508093.jpg
     * https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-508(*).jpg
     * https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-372.jpg
     * https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-129006.png
     * https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-129.jpg
     */
    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置超时间为3秒
        conn.setConnectTimeout(10 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        // 得到输入流
        InputStream inputStream = conn.getInputStream();
        // 获取自己数组
        byte[] getData = readInputStream(inputStream);

        // 文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }

        System.out.println(Thread.currentThread() + " info:" + url + " download success");

    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static List<Picture> initList(int k, String path) {
        List<Picture> list=new ArrayList<Picture>();
        if (path.startsWith("/")) {
            path = path + doFormat(k);
        } else {
            path = path + "/" + doFormat(k);
        }
        createDir(path);
        for (int j = 0; j < 1000; j++) {
            int i = j;
            String str = doFormat(i);
            String url = "https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-" + k + str + ".jpg";
            String name = "wallhaven-" + k + str + ".jpg";
            String filePath = path + "/" + name;
            String filePathOfPng = path + "/wallhaven-" + k + str + ".png";
            File file = new File(filePath);
            File file2 = new File(filePathOfPng);
            if (file.exists() || file2.exists()) {
                System.out.println(filePath + " exists");
                continue;
            }
            Picture noi=new Picture();
            noi.setName(name);
            noi.setPath(path);
            noi.setUrl(url);
            list.add(noi);
        }
        return list;
    }

    public static void DownNOI(int k, String path) {

        if (path.startsWith("/")) {
            path = path + doFormat(k);
        } else {
            path = path + "/" + doFormat(k);
        }
        createDir(path);

        for (int j = 0; j < 1000; j++) {
            int i = j;
            String str = doFormat(i);
            String url = "https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-" + k + str + ".jpg";
            String name = "wallhaven-" + k + str + ".jpg";
            String filePath = path + "/" + name;
            String filePathOfPng = path + "/wallhaven-" + k + str + ".png";
            File file = new File(filePath);
            File file2 = new File(filePathOfPng);
            if (file.exists() || file2.exists()) {
                System.out.println(filePath + " exists");
                continue;

            }
            try {
                downLoadFromUrl(url, name, path);
            } catch (IOException e) {
                url = "https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-" + k + str + ".png";
                name = "wallhaven-" + k + str + ".png";
                try {
                    downLoadFromUrl(url, name, path);
                } catch (IOException e1) {
                   System.err.println(e1.getMessage());
                }
            }
        }

    }

    // 验证字符串是否为正确路径名的正则表达式
    private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";
    // 通过 sPath.matches(matches) 方法的返回值判断是否正确
    // sPath 为路径字符串
    boolean flag = false;
    File file;

    public boolean DeleteFolder(String deletePath) {// 根据路径删除指定的目录或文件，无论存在与否
        flag = false;
        if (deletePath.matches(matches)) {
            file = new File(deletePath);
            if (!file.exists()) {// 判断目录或文件是否存在
                return flag; // 不存在返回 false
            } else {

                if (file.isFile()) {// 判断是否为文件
                    return deleteFile(deletePath);// 为文件时调用删除文件方法
                } else {
                    return deleteDirectory(deletePath);// 为目录时调用删除目录方法
                }
            }
        } else {
            System.out.println("要传入正确路径！");
            return false;
        }
    }

    public boolean deleteFile(String filePath) {// 删除单个文件
        flag = false;
        file = new File(filePath);
        if (file.isFile() && file.exists()) {// 路径为文件且不为空则进行删除
            file.delete();// 文件删除
            flag = true;
        }
        return flag;
    }

    public boolean deleteDirectory(String dirPath) {// 删除目录（文件夹）以及目录下的文件
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!dirPath.endsWith(File.separator)) {
            dirPath = dirPath + File.separator;
        }
        File dirFile = new File(dirPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();// 获得传入路径下的所有文件
        for (int i = 0; i < files.length; i++) {// 循环遍历删除文件夹下的所有文件(包括子目录)
            if (files[i].isFile()) {// 删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                System.out.println(files[i].getAbsolutePath() + " 删除成功");
                if (!flag) {
                    break;// 如果删除失败，则跳出
                }
            } else {// 运用递归，删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;// 如果删除失败，则跳出
                }
            }
        }
        if (!flag) {
            return false;
        }
        if (dirFile.delete()) {// 删除当前目录
            return true;
        } else {
            return false;
        }
    }

    // 创建单个文件
    public static boolean createFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {// 判断文件是否存在
            System.out.println("目标文件已存在" + filePath);
            return false;
        }
        if (filePath.endsWith(File.separator)) {// 判断文件是否为目录
            System.out.println("目标文件不能为目录！");
            return false;
        }
        if (!file.getParentFile().exists()) {// 判断目标文件所在的目录是否存在
            // 如果目标文件所在的文件夹不存在，则创建父文件夹
            System.out.println("目标文件所在目录不存在，准备创建它！");
            if (!file.getParentFile().mkdirs()) {// 判断创建目录是否成功
                System.out.println("创建目标文件所在的目录失败！");
                return false;
            }
        }
        try {
            if (file.createNewFile()) {// 创建目标文件
                System.out.println("创建文件成功:" + filePath);
                return true;
            } else {
                System.out.println("创建文件失败！");
                return false;
            }
        } catch (IOException e) {// 捕获异常
            e.printStackTrace();
            System.out.println("创建文件失败！" + e.getMessage());
            return false;
        }
    }

    // 创建目录
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {// 判断目录是否存在
            System.out.println("创建目录失败，目标目录已存在！");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
            destDirName = destDirName + File.separator;
        }
        if (dir.mkdirs()) {// 创建目标目录
            System.out.println("创建目录成功！" + destDirName);
            return true;
        } else {
            System.out.println("创建目录失败！");
            return false;
        }
    }

    // 创建临时文件
    public static String createTempFile(String prefix, String suffix, String dirName) {
        File tempFile = null;
        if (dirName == null) {// 目录如果为空
            try {
                tempFile = File.createTempFile(prefix, suffix);// 在默认文件夹下创建临时文件
                return tempFile.getCanonicalPath();// 返回临时文件的路径
            } catch (IOException e) {// 捕获异常
                e.printStackTrace();
                System.out.println("创建临时文件失败：" + e.getMessage());
                return null;
            }
        } else {
            // 指定目录存在
            File dir = new File(dirName);// 创建目录
            if (!dir.exists()) {
                // 如果目录不存在则创建目录
                if (createDir(dirName)) {
                    System.out.println("创建临时文件失败，不能创建临时文件所在的目录！");
                    return null;
                }
            }
            try {
                tempFile = File.createTempFile(prefix, suffix, dir);// 在指定目录下创建临时文件
                return tempFile.getCanonicalPath();// 返回临时文件的路径
            } catch (IOException e) {// 捕获异常
                e.printStackTrace();
                System.out.println("创建临时文件失败!" + e.getMessage());
                return null;
            }
        }
    }

    public static String doFormat(int i) {
        String str = String.format("%03d", i);
        // 0代表前面补零，3代表输出3位，根据你的需要修改即可。
        return str;
    }

    public static void createDir(int k) {

        String i = doFormat(k);
        String path = "d:/images/" + i;
        createDir(path);
    }

    public static String[] getFileName(String path) {
        File file = new File(path);
        String[] fileName = file.list();
        return fileName;
    }

    public static void getAllFileName(String path, ArrayList<String> fileName) {
        File file = new File(path);
        File[] files = file.listFiles();
        String[] names = file.list();
        if (names != null) {
            fileName.addAll(Arrays.asList(names));
        }
        for (File a : files) {
            if (a.isDirectory()) {
                getAllFileName(a.getAbsolutePath(), fileName);
            }
        }
    }

    public static Map<String, String> readSetFile() {
        Map<String, String> map = new HashMap<String, String>();
        try {
            String filepath = new File("").getAbsolutePath();
            filepath = filepath.replace('\\', '/') + "/set.ini";
            System.out.println("setfile path is " + filepath);
            Scanner in = new Scanner(new File(filepath));

            while (in.hasNextLine()) {
                String str = in.nextLine();
                str = str.trim();
                if (str.length() > 1) {
                    String[] abc = str.split("[\\p{Space}]+");
                    if (abc.length >= 2) {
                        String key = abc[0];
                        String value = abc[1];

                        map.put(key, value);
                    }

                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(map);
        return map;
    }

    public static Map<String, String> splitt(String str) {
        String strr = str.trim();
        String[] abc = strr.split(":");
        String key = abc[0];
        String value = abc[1];
        Map<String, String> map = new HashMap<String, String>();
        map.put(key, value);
        return map;
    }

    public static void writeIntoFile(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(conent + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        /*
         * for (int i = 100; i < 199; i++) { DownNOI(i); }
         */
        // DownNOI(99);
        Map<String, String> map = readSetFile();
        doDownLoad(map);
    }

    public static void doDownLoad(Map<String, String> map) {
        String path = map.get("path");
        int begin = Integer.parseInt(map.get("begin").trim());
        int end = Integer.parseInt(map.get("end").trim());
        if (begin > end) {
            System.out.println("设置信息错误");
        } else {
            for (int i = begin; i <= end; i++) {
                DownNOI(i, path);
            }
        }

    }
}
