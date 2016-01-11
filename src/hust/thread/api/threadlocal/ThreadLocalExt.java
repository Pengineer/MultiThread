package hust.thread.api.threadlocal;

import java.util.Date;

/**
 * 解决第一次从ThreadLocal中get值为空的问题：覆写initalValue()方法。
 * 
 * @author 2016-01-10
 *
 */
public class ThreadLocalExt extends ThreadLocal {

	@Override
	protected Object initialValue() {
		return new Date().getTime();  //不同的线程在不同的时刻调用，返回的值不一样，说明ThreadLocal中的变量时线程隔离的。
	}

}
