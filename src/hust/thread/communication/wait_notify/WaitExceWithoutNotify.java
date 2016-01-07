package hust.thread.communication.wait_notify;

/**
 * 执行wait后，不执行notify，线程将一直等待。
 * 
 * @author 2016-01-07
 *
 */
public class WaitExceWithoutNotify {

	public static void main(String[] args) {
		try {
			Object lock = new Object();
			synchronized(lock) {
				System.out.println("执行开始。。。");
				lock.wait();
				System.out.println("执行结束。。。");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
