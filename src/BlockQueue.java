import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * BlockingQueue 
 * 一台机器有3个任务，1、制作吐司 2、上黄油 3、 途果酱
 * @author liqiushi
 *
 */

/**
 * 吐司
 * 
 * @author liqiushi
 * 
 */
class Toast{
	public enum Status {
		DRY, BUTTERED, JAMMED
	}

	private final int id;
	private Status status = Status.DRY;

	public Toast(int id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void butter() {
		status = Status.BUTTERED;
	}

	public void jam() {
		status = Status.JAMMED;
	}

	public int getId() {
		return id;
	}

	public String toString() {
		return "toast " + id + ":" + status;
	}
}


 class ToastBlockQueue extends LinkedBlockingDeque<Toast> { }

/**
 * 制作吐司工
 * 
 * @author liqiushi
 * 
 */
class Toaster implements Runnable {

	private ToastBlockQueue toastBlockQueue;
	private int count = 0;
	public Toaster(ToastBlockQueue toastBlockQueue){
		this.toastBlockQueue = toastBlockQueue;
	}
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast toast = new Toast(count++);
				System.out.println(toast);
				// TimeUnit.SECONDS.sleep(1);
				toastBlockQueue.put(toast);
			}
		} catch (Exception e) {
			System.out.println("Toaster interrupted");
		}
		System.out.println("Toaster off");
	}
}

/**
 * 黄油线程
 * 
 * @author liqiushi
 * 
 */
class Butter implements Runnable {
	private ToastBlockQueue toastBlockQueue;
	private ToastBlockQueue butteredQueue;

	public Butter(ToastBlockQueue toastBlockQueue, ToastBlockQueue butteredQueue) {
		this.toastBlockQueue = toastBlockQueue;
		this.butteredQueue = butteredQueue;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast toast = toastBlockQueue.take();
				toast.butter();
				TimeUnit.SECONDS.sleep(1);
				System.out.println(toast);
				butteredQueue.put(toast);
			}
		} catch (Exception e) {
			System.out.println("Butter interrupted");
		}
		System.out.println("Butter off");
	}
}

class Jamer implements Runnable {
	private ToastBlockQueue butteredQueue;
	private ToastBlockQueue jamedQueue;

	public Jamer(ToastBlockQueue butteredQueue, ToastBlockQueue jamedQueue) {
		this.butteredQueue = butteredQueue;
		this.jamedQueue = jamedQueue;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast toast = butteredQueue.take();
				toast.jam();
				System.out.println(toast);
				jamedQueue.put(toast);
			}
		} catch (Exception e) {
			System.out.println("Jamer interrupted");
		}
		System.out.println("Jamer off");
	}
}

class Eater implements Runnable {
	private ToastBlockQueue jamedQueue;
	private int id = 0;
	public Eater(ToastBlockQueue jamedQueue) {
		this.jamedQueue = jamedQueue;
	}
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast toast = jamedQueue.take();
				if (toast.getId() != id++|| toast.getStatus() != Toast.Status.JAMMED) {
					System.out.println("err");
					System.exit(1);
				} else {
					System.out.println("eat!");
				}
			}
		} catch (Exception e) {
			System.out.println("eater interrupted");
		}
		System.out.println("eater off");
	}
}
/**
 * test
 * @author liqiushi
 *
 */
public class BlockQueue {
	public static void main(String[] args) throws InterruptedException {
		ToastBlockQueue dryQueue = new ToastBlockQueue();
		ToastBlockQueue butterQueue = new ToastBlockQueue();
		ToastBlockQueue jamQueue = new ToastBlockQueue();
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(new Toaster(dryQueue));
		executorService.execute(new Butter(dryQueue, butterQueue));
		executorService.execute(new Jamer(butterQueue, jamQueue));
		TimeUnit.SECONDS.sleep(1);
		executorService.shutdownNow();
	}


			}
