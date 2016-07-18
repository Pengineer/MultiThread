package hust.thread.api.threadlocal;

public class MyThreadB extends Thread {

	@Override
	public void run() {
		try {
			Thread.sleep(100);
			for(int i=0; i<5; i++) {
				System.out.println("ThreadB get: " + Tools.threadLocal.get());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
