package hust.thread.Synchronized.inheritable;

public class SubService extends ParService {

	@Override
	public void pMethod1_1() { 
		try {
			System.out.println("Parent start...");
			Thread.sleep(2000);
			System.out.println("Parent end...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
