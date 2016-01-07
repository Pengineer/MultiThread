package hust.thread.communication.wait_notify;

/**
 * 不在同步块中的wait执行时，抛IllegalMonitorStateException异常（该异常是RuntimeException的子类，不需要捕获）。
 * 
 * @author 2016-01-07
 *
 */
public class WaitExceNotInSync {

	public static void main(String[] args) throws InterruptedException {
		String str = new String("1234");
		str.wait();
	}

}
