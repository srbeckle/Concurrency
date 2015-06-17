package lesson10reentrantLocks;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * Use of reentrant lock. 
 * 
 * Call lock.lock to acquire lock. Similar to synchronized statement.
 * Call lock.unlock to release lock. Similar to notify.
 * 
 * Create Condition on lock....lock.newCondition.
 * condition.await == Object.wait
 * condition.signal == Object.notify  wakes up the waiting thread
 * 
 * @author steve
 *
 */
public class Runner {
	
	private int count = 0;
	private Lock lock = new ReentrantLock();
	private Condition cond = lock.newCondition();
	
	private void increment()
	{
		for(int i = 0; i < 10000; i++)
		{
			count++;
		}
	}
	
	public void firstThread()
	{
		lock.lock();
		try {
			System.out.println("Waiting");
			cond.await();
			System.out.println("Woken up in first thread");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  //hands over the lock
		try
		{
			increment(); //Put in try block in case exception is thrown unlock still gets called.
		}
		finally
		{
			lock.unlock();
		}
		
	}
	
	public void secondThread()
	{
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lock.lock();
		
		System.out.println("Press the return key....");
		new Scanner(System.in).nextLine();
		System.out.println("Got the return key....");
		
		cond.signal();  //Still must call unlock below to release lock. Analagous to calling notify but not having left synchronized block.
		
		try
		{
			increment();  
		}
		finally
		{
			lock.unlock();  //Must be called!!! signal is not enough. This is what releases the lock.
		}
	}
	
	public void finished()
	{
		System.out.println("Count is " + count);
	}
}
