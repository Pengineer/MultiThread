package hust.thread.communication.pr;

/**
 * 等待/通知模式最典型的案例就是"生产者与消费者"模式，此模式在使用上有多种变形，这里先讨论最简单的一种：
 * 一个生产者和一个消费者
 * 
 * @author 2016-01-07
 *
 */
public class OnePOneC {

	private static String value = "";
	
	public static void main(String[] args) {
		String lock = new String("lock");
		Producer p = new Producer(lock);
		Consumer c = new Consumer(lock);
		System.out.println("开始生产消费....");
		p.start();
		c.start();
	}
	
	//生产者:wait-notify
	static class Producer extends Thread {
		private String lock;
		public Producer(String lock) {
			this.lock = lock;
		}
		
		@Override
		public void run() {
			while(true) {
				try {
					synchronized(lock) {
						if(!OnePOneC.value.equals("")) {
							lock.wait();// 如果已经生产了一个，则等待消费者消费
						}
						OnePOneC.value = System.currentTimeMillis() + "_" + System.nanoTime();
						System.out.println("produce " + OnePOneC.value);
						lock.notify(); //生产完后提醒消费者消费
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//消费者:wait-notify
	static class Consumer extends Thread {
		private String lock = "";
		public Consumer(String lock) {
			this.lock = lock;
		}
		
		@Override
		public void run() {
			while(true) {
				try {
					synchronized(lock) {
						if(OnePOneC.value.equals("")) {
							lock.wait();// 如果已经消费完，则等待生产者生产
						}
						System.out.println("consume " + OnePOneC.value);
						OnePOneC.value = "";
						lock.notify(); //消费完后提醒生产者生产
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
