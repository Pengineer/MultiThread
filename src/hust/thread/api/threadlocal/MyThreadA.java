package hust.thread.api.threadlocal;

public class MyThreadA extends Thread {

	@Override
	public void run() {
		try {
			for(int i=0; i<5; i++) {
				System.out.println("ThreadA get: " + Tools.threadLocal.get());
			}
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
