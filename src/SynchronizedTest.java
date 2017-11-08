
public class SynchronizedTest implements Runnable{
	Timer timer = new Timer();
	public static void main(String[] args) {
		SynchronizedTest test = new SynchronizedTest();
		Thread aThread = new Thread(test);
		Thread bThread = new Thread(test);
		aThread.setName("a");
		bThread.setName("b");
		aThread.start();
		bThread.start();
	}
	
	public void run(){
		timer.add(Thread.currentThread().getName());
	}
}


class Timer{
	private static int num = 0;
	
	public synchronized void add(String threadName){
		//synchronized (this) {
			num ++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ;
			}
			System.out.println(threadName+" "+num);
		//}

	}
}