package hust.thread.communication.pr;

/**
 * 多个生产者和多个消费者：基于OnePOneC做的修改
 * 
 * @author 2016-01-08
 *
 */
public class ManyPManyC {
	private static String value = "";
	
	public static void main(String[] args) {
		String lock = new String("lock");
		Producer p1 = new Producer(lock);
		Producer p2 = new Producer(lock);
		Consumer c1 = new Consumer(lock);
		Consumer c2 = new Consumer(lock);
		p1.setName("生产者1");
		p2.setName("生产者2");
		c1.setName("消费者1");
		c2.setName("消费者2");
		p1.start();
		p2.start();
		c1.start();
		c2.start();
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
						while(!ManyPManyC.value.equals("")) { //因为最多只能有一个产品，有可能被其他生产者生产，因此用while
							System.out.println(Thread.currentThread().getName() + " wait");
							lock.wait();
						}
						ManyPManyC.value = System.currentTimeMillis() + "_" + System.nanoTime();
						System.out.println(Thread.currentThread().getName() + " produce " + ManyPManyC.value);
						lock.notifyAll(); //必须使用notifyAll，否则如果连续多次出现同类唤醒同类的情况会导致程序"假死"————所有线程都wait了
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
						while(ManyPManyC.value.equals("")) {
							System.out.println(Thread.currentThread().getName() + " wait");
							lock.wait();
						}
						System.out.println(Thread.currentThread().getName() + " consume " + ManyPManyC.value);
						ManyPManyC.value = "";
						lock.notifyAll();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
