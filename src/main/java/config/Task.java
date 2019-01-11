package config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Configurable
@EnableScheduling
@EnableAsync
public class Task {


    @Scheduled(cron = "0/5 * * * * ?")
    @Async
    public void websocketMsgTask() {

        sleep(1000);
        System.out.println("***********");
    }


    public static void main(String[] args) {
        HavenProject wh = new HavenProject();
        Map<String, String> map = HavenProject.readSetFile();

        String path = map.get("path");
        int thread = Integer.parseInt(map.get("thread").trim());
        int begin = Integer.parseInt(map.get("begin").trim());
        int end = Integer.parseInt(map.get("end").trim());
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(thread);
        if (begin > end) {
            System.out.println("设置信息错误");
        } else {
            for (int i = begin; i <= end; i++) {
                // DownNOI(i, path);
                fixedThreadPool.execute(new RunnableHaven(i, path));
            }
        }

    }

    private int sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
        }
        return ms;
    }
}
