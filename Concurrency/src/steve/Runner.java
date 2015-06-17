package steve;

import java.util.Deque;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.Timer;

public class Runner 
{
	Lock lock = new ReentrantLock();
	Condition readyToRetrieve = lock.newCondition();
	Condition needToProduce = lock.newCondition();
	Condition empty = lock.newCondition();
	
	Deque<String> mailbox = new ConcurrentLinkedDeque<>();
	final static int LIMIT = 5;
	Random random = new Random();
	Timer timer;
	int counter = 1;
	
	public void produce()
	{
		boolean gotLockInProducer = false;
		while (true) {
			try {
				gotLockInProducer = lock.tryLock();
				System.out.println("Producer " + (gotLockInProducer ? "did " : "did not ") + " get lock");
				while (mailbox.size() == LIMIT) {
					needToProduce.await(); //releases lock
				}
				//proceed
				System.out.println("Producer wakes up");
				Thread.sleep(random.nextInt(15));
				
				
				String msg = "Message put in mailbox: Msg # " + counter++;
				mailbox.addLast(msg);
				System.out.println(msg +"  Size = " + mailbox.size());
				System.out.println("Signal consumer");
				readyToRetrieve.signal();
			} catch (Exception e) {
				// TODO: handle exception
			}
			finally
			{
				System.out.println("Producer release lock");
				if (gotLockInProducer) {
					lock.unlock();
				}
			}
		}
		
	}
	
	public void consume()
	{
		boolean gotLockInConsumer = false;
		try {
			Thread.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {
			try {
				gotLockInConsumer = lock.tryLock();
				System.out.println("Consumer " + (gotLockInConsumer ? "did " : "did not ") + " get lock");
				while (mailbox.size() == 0) {
					readyToRetrieve.await(); //releases lock
				}
				//proceed
				System.out.println("Consumer wakes up");
				System.out.println("Press return to get message.....");
				new Scanner(System.in).nextLine();
				System.out.println("Getting message " + mailbox.peekFirst());
				mailbox.removeFirst();
				System.out.println("Size after getting message = " + mailbox.size());
				needToProduce.signal();
			} catch (Exception e) {
				// TODO: handle exception
			}
			finally
			{
				System.out.println("Consumer releases lock");
				if (gotLockInConsumer) {
					lock.unlock();
				}
			}
		}
	}
	
	public void cleanUp()
	{
		
	}
}
