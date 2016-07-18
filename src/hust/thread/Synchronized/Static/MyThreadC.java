package hust.thread.Synchronized.Static;

public class MyThreadC extends Thread {
	
	@Override
	public void run() {
		methodC2(); //补充一下：非静态方法可以直接调用静态方法；反之不行，但是可以通过传引用的方式在静态方法中调用非静态方法。
	}

	synchronized public static void methodC1() {
		try {
			System.out.println(Thread.currentThread().getName() + " start m1...");
			Thread.sleep(3000);
			System.out.println(Thread.currentThread().getName() + " end m1...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	synchronized public void methodC2() {
		try {
			System.out.println(Thread.currentThread().getName() + " start m2...");
			Thread.sleep(3000);
			System.out.println(Thread.currentThread().getName() + " end m2...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
