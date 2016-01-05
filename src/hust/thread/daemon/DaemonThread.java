package hust.thread.daemon;

/**
 * Java线程分两种，一种是用户线程，一种是守护线程。
 * 守护有“一直陪伴”的意思，当进程中不存在非守护线程时，守护线程就会自动销毁。典型的守护线程应用是GC。
 * 
 * @author 2015-01-05
 *
 */
public class DaemonThread {

	public static void main(String[] args) throws InterruptedException {
		MyThread t = new MyThread(); //t线程格式守护线程
		t.setDaemon(true);
		t.start();
		
		Thread.sleep(5000); //main线程是用户线程
		System.out.println("用户线程执行5s结束后，守护线程也会自动停止");
	}
	
	static class MyThread extends Thread {
		private int i=0;
		@Override
		public void run() {
			try {
				while(true) {
					i++;
					System.out.println(i);
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
