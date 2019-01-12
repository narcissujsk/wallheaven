package wall.executeonce;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import wall.heaven.HavenProject;
import wall.heaven.Picture;
import wall.util.GsonUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

/**
 * @program: wallheaven
 * 在服务启动的时候执行代码的方法
 * @description:
 * @author: jiangsk@inspur.com
 * @create: 2019-01-11 17:21
 **/
@Component
@Order(value = 1)
public class MyApplicationRunner implements ApplicationRunner {
    public static ConcurrentLinkedQueue<Picture> lists = new ConcurrentLinkedQueue<Picture>();
    Logger logger=Logger.getLogger("MyApplicationRunner");
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("init the download lists");
        Map<String, String> map = HavenProject.readSetFile();
        String path = map.get("path");
        int thread = Integer.parseInt(map.get("thread").trim());
        int begin = Integer.parseInt(map.get("begin").trim());
        int end = Integer.parseInt(map.get("end").trim());
        for (int i =begin; i < end; i++) {
            List<Picture> list = HavenProject.initList(i, path);
            lists.addAll(list);
            logger.info(list.size()+"");
           /* for (int j = 0; j < list.size(); j++) {
                logger.info(GsonUtil.toJson(list.get(i)));
            }*/
        }



    }
}
