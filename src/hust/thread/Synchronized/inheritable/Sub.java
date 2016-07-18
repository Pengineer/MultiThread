package hust.thread.Synchronized.inheritable;

public class Sub extends Thread {
	
	private SubService subService;
	public Sub(SubService subService) {
		this.subService = subService;
	}
	
	@Override
	public void run() {
//		subService.pMethod1_1();  // 如果子类重写了父类的同步方法，那么子类的该方法不再同步
		subService.pMethod1_2();  // 如果子类没有重写父类的同步方法，那么通过子类的引用去调用该方法时，仍然是同步的。
	}
}
