package lesson4MultipleLocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker {

	private List<Integer> list1 = new ArrayList<>();
	private List<Integer> list2 = new ArrayList<>();
	
	private Random random = new Random();
	
	public synchronized void stageOne()
	{
		synchronized(list1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list1.add(random.nextInt(100));
		}
	}
	
	public void stageTwo()
	{
		synchronized(list2) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list2.add(random.nextInt(100));
		}
	}
	
	public void process()
	{
		for(int i = 0; i < 1000; i++)
		{
			stageOne();
			stageTwo();
		}
	}
	
	public Worker() {
		// TODO Auto-generated constructor stub
	}

	public  void main() {
		System.out.println("Starting....");
		long start = System.currentTimeMillis();
		
		Thread t1 = new Thread(()->process());
		t1.start();
				
		Thread t2 = new Thread(()->process());
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		System.out.println("Time taken = " + (end - start));
		System.out.println("List1 size: " + list1.size() + "  List2 size: " + list2.size());

	}

}
