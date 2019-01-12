package wall.heaven;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;


public class Main {
	public static void main(String[] args) {
		Map<String, String> map = HavenProject.readSetFile();
		
		String path = map.get("path");
		int thread = Integer.parseInt(map.get("thread").trim());
		int begin = Integer.parseInt(map.get("begin").trim());
		int end = Integer.parseInt(map.get("end").trim());
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(thread);
        executor.setMaxPoolSize(thread);
        executor.setQueueCapacity(thread);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("cps");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
		if (begin > end) {
			System.out.println("设置信息错误");
		} else {
			for (int i = begin; i <= end; i++) {
				// DownNOI(i, path);
                executor.execute(new RunnableHaven(i, path));
			}
		}

	}
}
