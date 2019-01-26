package wall.download;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import wall.executeonce.MyApplicationRunner;
import wall.heaven.HavenProject;
import wall.heaven.Picture;
import wall.util.GsonUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

@Component
@Configurable
@EnableScheduling
@EnableAsync
public class DownloadTask {
    Logger log = Logger.getLogger("DownloadTask");

    @Scheduled(cron = "0/1 * * * * ?")
    @Async
    public void downLoadTask() {
        executeTask();
    }

    private void executeTask() {
        long beginTime = System.currentTimeMillis();
        if (MyApplicationRunner.lists.isEmpty()) {
            return;
        }
        Picture picture;
        picture = MyApplicationRunner.lists.poll();
        if (picture == null) {
            return;
        }
        //log.info(Thread.currentThread().getName() + "begin download picture :" + GsonUtil.toJson(picture) + "");
        try {
            downLoadFromUrl(picture.getUrl(), picture.getName(), picture.getPath());
        } catch (IOException e) {
            log.info("***********"+e.getMessage());
            try {
                downLoadFromUrl(picture.getUrl().replace(".jpg", ".png"), picture.getName().replace(".jpg", ".png"), picture.getPath());
            } catch (IOException e1) {
                log.info("***********"+e1.getMessage());
            }
        }
        long endTime = System.currentTimeMillis();
        double diffTime = (endTime - beginTime)/1000.0;
       // log.info(Thread.currentThread().getName() + " executed download : "+picture.getName()+" { cost = " + diffTime + "}");
        Boolean isSucess = false;
        return;
    }


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
    public void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置超时间为3秒
        conn.setConnectTimeout(60 * 1000);
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

        log.info(Thread.currentThread() + " info:" + fileName + " download success");
        return;
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
}
