package wallheaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"wall.config", "wall.executeonce","wall"})
public class WallheavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(WallheavenApplication.class, args);

    }

}

