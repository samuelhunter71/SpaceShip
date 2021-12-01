package math;

public class Chrono {
	
	private long delta, lastTime;
	private long time;
	private boolean isRunning;
	
	public Chrono() {
		delta=0;
		lastTime=System.currentTimeMillis();
		isRunning=false;
	}
	
	public void run(long time) {
		isRunning=true;
		this.time=time;
	}
	
	public void update() {
		if(isRunning)
			delta+=System.currentTimeMillis()-lastTime;
		
		if(delta>=time) {
			isRunning=false;
			delta=0;
		}	
		
		lastTime=System.currentTimeMillis();
	}
	
	public boolean isRunning() {
		return isRunning;
	}

}
