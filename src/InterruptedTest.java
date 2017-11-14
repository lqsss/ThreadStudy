public class InterruptedTest {
	public static void main(String[] args) {
		Thread test = new Thread(new Runner8());
		test.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.interrupt();
	}
}

class Runner8 implements Runnable {

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			System.out.println(Thread.currentThread().isInterrupted()); // true
		}

		try {
			Thread.sleep(1000);

		} catch (InterruptedException e) {
			System.out
					.println("sleep" + Thread.currentThread().isInterrupted());
			System.out.println(e.getLocalizedMessage());
		}
		System.out.println(Thread.currentThread().isInterrupted()
				+ " After while!");
	}

}
