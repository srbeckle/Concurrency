package lesson6CountdownLatches;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//When should we use CountDownLatch in Java :
//
//Use CountDownLatch when one of Thread like main thread, require to wait for one or more thread to complete, 
//before its start doing processing. Classical example of using CountDownLatch in Java  is any server 
//side core Java application which uses services architecture,  where multiple services is provided by multiple 
//threads and application can not start processing  until all services have started successfully as shown in our CountDownLatch example.


class Processor implements Runnable
{
	private CountDownLatch latch;
	
	public Processor(CountDownLatch latch)
	{
		this.latch = latch;
	}

	@Override
	public void run() {
		
		System.out.println("Started.");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		latch.countDown();
		
	}
}


public class App {

	public static void main(String[] args) {
		
		CountDownLatch latch = new CountDownLatch(3); //lets threads wait until latch count reaches 0
		ExecutorService executor = Executors.newFixedThreadPool(3);
		for(int i = 0; i < 3; i++)
		{
			executor.submit(new Processor(latch));
		}
		
		try {
			latch.await();  //waits until latch = 0
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Completed");  //get here when finished waiting
		
		executor.shutdown();
	}

}
