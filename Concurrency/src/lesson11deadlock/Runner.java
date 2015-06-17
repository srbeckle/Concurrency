package lesson11deadlock;

import java.util.Random;
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
	
	private Account acc1 = new Account();
	private Account acc2 = new Account();
	
	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();
	
	private Lock lock = new ReentrantLock();
	
	private void acquireLocks(Lock firstLock, Lock secondLock)
	{
		while(true)
		{
			boolean gotFirst = false;
			boolean gotSecond = false;
			//Acquire locks
			try
			{
				gotFirst = firstLock.tryLock();
				gotSecond = secondLock.tryLock();
			}
			finally
			{
				if(gotFirst && gotSecond) return;
				
				if(gotFirst) firstLock.unlock();
				if(gotSecond) secondLock.unlock();
			}
			
			//Locks not acquired
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void firstThread()
	{
		Random random = new Random();
		for(int i  = 0; i < 10000; i++)
		{
			acquireLocks(lock1, lock2);
			try {
				Account.transfer(acc1, acc2, random.nextInt(100));
			}
			finally
			{
				lock2.unlock();
				lock1.unlock();
			}
		}
		
	}
	
	public void secondThread()
	{
		Random random = new Random();
		for(int i  = 0; i < 10000; i++)
		{
			acquireLocks(lock2, lock1);
			try {
				Account.transfer(acc2, acc1, random.nextInt(100));
			}
			finally
			{
				lock2.unlock();
				lock1.unlock();
			}
			
		}
	}
	
	public void finished()
	{
		System.out.println("Account 1 balance: " + acc1.getBalance());
		System.out.println("Account 2 balance: " + acc2.getBalance());
		System.out.println("Total balance: " + (acc1.getBalance() + acc2.getBalance()));
	}
}
