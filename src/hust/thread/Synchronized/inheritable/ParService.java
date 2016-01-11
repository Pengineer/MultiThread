package hust.thread.Synchronized.inheritable;

public class ParService {
	
	synchronized public void pMethod1_1() {
		try {
			System.out.println("Parent start m2_1...");
			Thread.sleep(3000);
			System.out.println("Parent end m2_1...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	synchronized public void pMethod1_2() {
		try {
			System.out.println("Parent start m2_2...");
			Thread.sleep(3000);
			System.out.println("Parent end m2_2...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
