package wallheaven;

import config.HavenProject;
import config.RunnableHaven;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
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
}
