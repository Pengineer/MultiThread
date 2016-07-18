package hust.thread.Synchronized.Static;

public class MyThreadA extends Thread {
	private Service service;
	public MyThreadA(Service service) {
		this.service = service;
	}
	
	@Override
	public void run() {
		service.method1();
	}
}
