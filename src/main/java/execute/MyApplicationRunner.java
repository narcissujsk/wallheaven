package execute;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
    Logger logger=Logger.getLogger("MyApplicationRunner");
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("MyApplicationRunner");
    }
}
