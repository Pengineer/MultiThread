package hust.thread.simple;

public class ThreadName {

	public static void main(String[] args) {
		MyThread t = new MyThread();
		Thread tt = new Thread(t, "B"); //实际上这里有两个Thread对象，t作为tt的target参数传递进来，那么在启动tt的时候，就会调用t的run方法。
		tt.start();
//		t.start();
	}
	
	static class MyThread extends Thread {
		
		@Override
		public void run() {
			super.run();
			this.setName("A"); //设置thread的名字
			System.out.println(this.getName());
			System.out.println(Thread.currentThread().getName()); //调用本Thread的run方法的Thread名字。
		}
	}
}
