package wall.config;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import wall.heaven.HavenProject;

/**
 * @program: wallheaven
 * @description:
 * @author: jiangsk@inspur.com
 * @create: 2019-01-11 16:42
 **/
@Service
public class TaskService {
    //@Async
    public void executeAsyncTask(int i) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("线程" + Thread.currentThread().getName() + " 执行异步任务：" + i);
    }

    @Async
    public void download(int i, String path) throws InterruptedException {
        System.out.println("线程" + Thread.currentThread().getName() + " 执行异步任务：" + i);
        HavenProject.DownNOI(i, path);
    }
}


