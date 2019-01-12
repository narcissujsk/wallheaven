package wallheaven;

import wall.heaven.HavenProject;
import wall.config.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WallheavenApplicationTests {

    @Test
    public void contextLoads() {
        Map<String, String> map = HavenProject.readSetFile();

        String path = map.get("path");
        int thread = Integer.parseInt(map.get("thread").trim());
        int begin = Integer.parseInt(map.get("begin").trim());
        int end = Integer.parseInt(map.get("end").trim());

        for (int i = begin; i <= end; i++) {
            // DownNOI(i, path);
            try {
                asyncTaskService.download(i, path);
            } catch (InterruptedException e) {


            }
        }
        while (true) {
        }

    }

    @Autowired
    private TaskService asyncTaskService;

    @Test
    public void threadTest() throws InterruptedException {
        for (int i =33; i <733; i++) {
            asyncTaskService.executeAsyncTask(i);
        }
    }

}

