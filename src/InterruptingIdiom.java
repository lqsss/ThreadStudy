import java.util.concurrent.TimeUnit;

class NeedsCleanUp {
	private final int id;

	public NeedsCleanUp(int ident) {
		this.id = ident;
		System.out.println("NeedsCleanUp " + id);
	}

	public void cleanUp() {
		System.out.println("cleanUp " + id);
	}
}

class Block3 implements Runnable {
	private volatile double d = 0.0;

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// point1
				NeedsCleanUp n1 = new NeedsCleanUp(1);
				try {
					System.out.println("Sleeping");
					TimeUnit.SECONDS.sleep(1);
					// point2
					NeedsCleanUp n2 = new NeedsCleanUp(2);
					try {
						System.out.println("Calculating");
						for (int i = 0; i < 2500000; i++) {
							d = d + (Math.PI + Math.E) / d;
						}
						System.out.println("Finished time-consuming opration");
					} finally {
						n2.cleanUp();
					}
				} finally {
					n1.cleanUp();
				}
			}
			System.out.println("Exiting while() test");
		} catch (InterruptedException e) {
			System.out.println("Exiting via InterruptedException");
		}
	}
}

public class InterruptingIdiom {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("usage: java InterruptingIdiom delay-in-mS");
			System.exit(1);
		}
		Thread t = new Thread(new Block3());
		t.start();
		TimeUnit.MILLISECONDS.sleep(new Integer(args[0]));
		// t.interrupt();
	}

}