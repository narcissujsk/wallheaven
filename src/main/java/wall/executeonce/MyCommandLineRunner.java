package wall.executeonce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @program: wallheaven
 * @description:
 * @author: jiangsk@inspur.com
 * @create: 2019-01-11 17:18
 **/
//@Component
public class MyCommandLineRunner implements CommandLineRunner, Ordered {
    Logger logger=Logger.getLogger("MyCommandLineRunner");
    @Override
    public void run(String... args) throws Exception {
        logger.info("MyCommandLineRunner");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
