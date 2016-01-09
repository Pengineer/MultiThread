package hust.thread.communication.wait_notify;

/**
 * wait表示的是终止当前线程，与调用者无关。
 * 
 * @author 2016-01-09
 *
 */
public class TestWait {
	public static void main(String[] args) {
		try {
			System.out.println("main start");
			MyThread t = new MyThread();
			t.start();
			synchronized(t) {
				System.out.println("business start");
				t.wait(0); //让main等待，而不是线程t，线程t执行完毕后，释放锁，main重新得到对象锁。注意了wait(0)=wait().
				System.out.println("business process");
				t.wait(0); //再也不会有被唤醒的条件了，main线程将一直等待下去。
				System.out.println("business end");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main end");
	}
	
	static class MyThread extends Thread {

		@Override
		public synchronized void run() {
			System.out.println("sub thread start...");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("sub thread end...");
		}
		
	} 

}
