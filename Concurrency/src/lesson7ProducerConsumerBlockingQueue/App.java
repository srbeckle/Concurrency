package lesson7ProducerConsumerBlockingQueue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {
	
	private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);   //FIFO  thread safe

	public static void main(String[] args)  {
		Thread t1 = new Thread(()->producer());
		Thread t2 = new Thread(()->consumer());
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void producer() 
	{
		Random random = new Random();
		while(true)
		{
			try {
				queue.put(random.nextInt(100));  //++++++++++//blocks if queue full
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
	}
	
	private static void consumer()
	{
		Random random = new Random();
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(random.nextInt(10) == 0)
			{
				Integer value = null;
				try {
					value = queue.take();  //++++++++++++waits until something in queue if empty
					
					System.out.println("Taken value " + value + ". Queue size is " + queue.size());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				
			}
		}
	}

}
