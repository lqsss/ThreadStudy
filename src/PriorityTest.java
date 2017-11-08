
public class PriorityTest {
	public static void main(String[] args) {
		Thread aThread = new Thread(new Runner6());
		Thread bThread = new Thread(new Runner7());
		aThread.setPriority(Thread.NORM_PRIORITY+2);
		aThread.start();bThread.start();
	}
}

class Runner6 implements Runnable{
	public void run() {
		for(int i = 0 ; i<= 20; i++){
			System.out.println("A" + i);
		}
	}
}

class Runner7 implements Runnable{
	public void run() {
		for(int i = 0 ; i<= 20; i++){
			System.out.println("B" + i);
		}
	}
}