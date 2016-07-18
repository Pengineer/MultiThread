package hust.thread.Synchronized.inheritable;

/**
 * 同步方法的继承问题（需要分情况讨论，网上很多说法甚至书上都是错的）
 * 
 * 如果子类重写了父类的同步方法，那么子类的该方法不再同步。
 * 如果子类没有重写父类的同步方法，那么通过子类的引用去调用该方法时，仍然是同步的。
 * 
 * @author 2016-01-11
 *
 */
public class MainClass {

	public static void main(String[] args) {
		SubService service = new SubService();
		Sub s1 = new Sub(service);
		Sub s2 = new Sub(service);
		
		s1.start();
		s2.start();
	}
}
