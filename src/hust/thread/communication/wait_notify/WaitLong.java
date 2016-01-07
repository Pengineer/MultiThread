package hust.thread.communication.wait_notify;

/**
 * 方法wait(long)的功能是在在指定的时间范围内，如果线程有被唤醒，则直接变成Runnable状态；否则，在超过设定的时间范围时，自动被唤醒。
 * 
 * @author 2016-01-07
 *
 */
public class WaitLong {

	private static Object lock = new Object();
	
	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					synchronized(lock) {
						System.out.println("start...." + System.currentTimeMillis());
						lock.wait(3000);
						System.out.println("end......" + System.currentTimeMillis());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
}
