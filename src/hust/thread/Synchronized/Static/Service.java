package hust.thread.Synchronized.Static;

public class Service {
	public static synchronized void method1() {
		try {
			System.out.println(Thread.currentThread().getName() + "进入method1");
			Thread.sleep(3000);
			System.out.println(Thread.currentThread().getName() + "离开method1");
		} catch (InterruptedException e) {
			
		}
	}
	
	public synchronized void method2() {
		try {
			System.out.println(Thread.currentThread().getName() + "进入method2");
			Thread.sleep(3000);
			System.out.println(Thread.currentThread().getName() + "离开method2");
		} catch (InterruptedException e) {
			
		}
	}
}
