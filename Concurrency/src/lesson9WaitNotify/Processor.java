package lesson9WaitNotify;

import java.util.LinkedList;
import java.util.Random;

public class Processor {

	private LinkedList<Integer> list = new LinkedList<>();
	private final int LIMIT = 10;
	private Object lock = new Object();


	public void produce()
	{
		int value = 0;
		while(true)
		{
			synchronized (lock) {
				while (list.size() == LIMIT) {
					try {
						System.out.println("Wait");
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("Adding");
				list.add(value++);
			}
		}
	}

	public void consume() 
	{
		Random random = new Random();
		while(true)
		{
			synchronized (lock) {
				while (list.size() == 0 ) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				System.out.print("List size is: " + list.size());
				int value = list.remove();
				System.out.println("   value is " + value);
				lock.notify();
			}
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
