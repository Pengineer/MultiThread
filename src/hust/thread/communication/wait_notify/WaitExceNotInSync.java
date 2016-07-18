package hust.thread.communication.wait_notify;

/**
 * 不在同步块中的wait执行时，抛IllegalMonitorStateException异常（该异常是RuntimeException的子类，不需要捕获）。
 * 
 * @author 2016-01-07
 *
 */
public class WaitExceNotInSync {

	public static void main(String[] args) { 
		try {
			String str = new String("1234");
			str.wait();
		} catch (InterruptedException e) {//当处于wait状态的线程遇到interrupt方法时，会抛出InterruptedException异常
			e.printStackTrace();
		}
		
	}

}
