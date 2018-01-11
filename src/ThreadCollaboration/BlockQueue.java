package ThreadCollaboration;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class Toast {
	public enum Status {
		DRY, BUTTERED, JAMMED, READY {
			public String toString() {
				return BUTTERED.toString() + " & " + JAMMED.toString();
			}
		}
	}

	private Status status = Status.DRY;
	private final int id;

	public Toast(int idn) {
		id = idn;
	}

	public void butter() {
		status = (status == Status.DRY) ? Status.BUTTERED : Status.READY;
	}

	public void jam() {
		status = (status == Status.DRY) ? Status.JAMMED : Status.READY;
	}

	public Status getStatus() {
		return status;
	}

	public int getId() {
		return id;
	}

	public String toString() {
		return "Toast " + id + ": " + status;
	}
}

class ToastQueue extends LinkedBlockingQueue<Toast> {
}

class Toaster implements Runnable {
	private ToastQueue toastQueue;
	private int count;
	private Random rand = new Random(47);

	public Toaster(ToastQueue tq) {
		toastQueue = tq;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
				// Make toast
				Toast t = new Toast(count++);
				System.out.println(t);
				// Insert into queue
				toastQueue.put(t);
			}
		} catch (InterruptedException e) {
			System.out.println("Toaster interrupted");
		}
		System.out.println("Toaster off");
	}
}

// Apply butter to toast:
class Butterer implements Runnable {
	private ToastQueue inQueue, butteredQueue;

	public Butterer(ToastQueue in, ToastQueue buttered) {
		inQueue = in;
		butteredQueue = buttered;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Blocks until next piece of toast is available:
				Toast t = inQueue.take();
				t.butter();
				System.out.println(t);
				butteredQueue.put(t);
			}
		} catch (InterruptedException e) {
			System.out.println("Butterer interrupted");
		}
		System.out.println("Butterer off");
	}
}

// Apply jam to toast:
class Jammer implements Runnable {
	private ToastQueue inQueue, jammedQueue;

	public Jammer(ToastQueue in, ToastQueue jammed) {
		inQueue = in;
		jammedQueue = jammed;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Blocks until next piece of toast is available:
				Toast t = inQueue.take();
				t.jam();
				System.out.println(t);
				jammedQueue.put(t);
			}
		} catch (InterruptedException e) {
			System.out.println("Jammer interrupted");
		}
		System.out.println("Jammer off");
	}
}

// Consume the toast:
class Eater implements Runnable {
	private ToastQueue finishedQueue;

	public Eater(ToastQueue finished) {
		finishedQueue = finished;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Blocks until next piece of toast is available:
				Toast t = finishedQueue.take();
				// Verify that all pieces are ready for consumption:
				if (t.getStatus() != Toast.Status.READY) {
					System.out.println(">>>> Error: " + t);
					System.exit(1);
				} else
					System.out.println("Chomp! " + t);
			}
		} catch (InterruptedException e) {
			System.out.println("Eater interrupted");
		}
		System.out.println("Eater off");
	}
}

// Outputs alternate inputs on alternate channels:
// 将dry分开成两个工作，一个黄油，一个酱
class Alternator implements Runnable {
	private ToastQueue inQueue, out1Queue, out2Queue;
	private boolean outTo2; // control alternation

	public Alternator(ToastQueue in, ToastQueue out1, ToastQueue out2) {
		inQueue = in;
		out1Queue = out1;
		out2Queue = out2;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Blocks until next piece of toast is available:
				Toast t = inQueue.take();
				if (!outTo2)
					out1Queue.put(t);
				else
					out2Queue.put(t);
				outTo2 = !outTo2; // change state for next time
			}
		} catch (InterruptedException e) {
			System.out.println("Alternator interrupted");
		}
		System.out.println("Alternator off");
	}
}

// Accepts toasts on either channel, and relays them on to
// a "single" successor
// 合并两个工作，如果是黄油就进行涂酱，反之，（异步）如果是ready就进入完成队列
class Merger implements Runnable {
	private ToastQueue in1Queue, in2Queue, toBeButteredQueue, toBeJammedQueue,
			finishedQueue;

	public Merger(ToastQueue in1, ToastQueue in2, ToastQueue toBeButtered,
			ToastQueue toBeJammed, ToastQueue finished) {
		in1Queue = in1;
		in2Queue = in2;
		toBeButteredQueue = toBeButtered;
		toBeJammedQueue = toBeJammed;
		finishedQueue = finished;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Blocks until next piece of toast is available:
				Toast t = null;
				// 数据既可以来自黄油队列，也可以来自酱队列
				// 所以不用无限期的等待
				while (t == null) {
					t = in1Queue.poll(50, TimeUnit.MILLISECONDS);
					if (t != null)
						break;
					t = in2Queue.poll(50, TimeUnit.MILLISECONDS);
				}
				// Relay toast onto the proper queue
				switch (t.getStatus()) {
				case BUTTERED:
					toBeJammedQueue.put(t);
					break;
				case JAMMED:
					toBeButteredQueue.put(t);
					break;
				default:
					finishedQueue.put(t);
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Merger interrupted");
		}
		System.out.println("Merger off");
	}
}

public class BlockQueue {
	public static void main(String[] args) throws Exception {
/*		Set<String> set = new HashSet<>();
		set.add("1");
		set.add("2");
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("1", 1);
		map.put("2", 2);
		for (Iterator<String> iterator = map.keySet().iterator(); iterator
				.hasNext();) {
			String string = iterator.next();
			System.out.println(string);
		}*/
		// ToastQueue dryQueue = new ToastQueue(), butteredQueue = new
		// ToastQueue(), toBeButteredQueue = new ToastQueue(), jammedQueue = new
		// ToastQueue(), toBeJammedQueue = new ToastQueue(), finishedQueue = new
		// ToastQueue();
		// ExecutorService exec = Executors.newCachedThreadPool();
		// exec.execute(new Toaster(dryQueue));
		// exec.execute(new Alternator(dryQueue, toBeButteredQueue,
		// toBeJammedQueue));
		// exec.execute(new Butterer(toBeButteredQueue, butteredQueue));
		// exec.execute(new Jammer(toBeJammedQueue, jammedQueue));
		// exec.execute(new Merger(butteredQueue, jammedQueue,
		// toBeButteredQueue,
		// toBeJammedQueue, finishedQueue));
		// exec.execute(new Eater(finishedQueue));
		// TimeUnit.SECONDS.sleep(5);
		// exec.shutdownNow();
	}
} /* (Execute to see output) */// :~