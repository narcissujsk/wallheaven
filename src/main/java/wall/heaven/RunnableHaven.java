package wall.heaven;

public class RunnableHaven implements Runnable {
	private int i;
	private String path;
	public RunnableHaven(int i,String path) {
		this.setI(i);
		this.setPath(path);
	}

	@Override
    public void run() {
        HavenProject.DownNOI(i, path);
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	

}
