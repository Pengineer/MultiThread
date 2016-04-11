package hust.callable;

import java.util.concurrent.Callable;

/**
 * 任务类
 * 
 * @author liangjian
 *
 */
public class Task implements Callable<Integer> {

	private int end;
	public Task(int end) {
		this.end = end;
	}
	
	@Override
	public Integer call() throws Exception {
		return fabo(end);
	}
	
	/*
	 * 业务方法
	 */
	public static int fabo(int num) {
		if(num == 1) {
			return 1;
		}else if(num == 2) {
			return 1;
		}else{
			return fabo(num-1) + fabo(num-2);
		}
	}

}
