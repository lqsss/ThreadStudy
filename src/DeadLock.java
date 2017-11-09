
public class DeadLock implements Runnable{
	public static Object o1 = new Object();
	public static Object o2 = new Object();
	public int flag = -1;
	public static void main(String args[]) {
		// TODO Auto-generated method stub
		DeadLock deadLock1 = new DeadLock();
		DeadLock deadLock2 = new DeadLock();

		deadLock1.flag = 1;
		deadLock2.flag = 0;
		Thread aThread = new Thread(deadLock1);
		Thread bThread = new Thread(deadLock2);
		
		aThread.start();bThread.start();
	}

	@Override
	public void run() {
		System.out.println(this);
		// TODO Auto-generated method stub
		if(flag == 1){
			synchronized (o1) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				synchronized (o2){
					System.out.println(flag+" o1");
				}
				
			}
		}
		
		if(flag == 0){
			synchronized (o2) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				synchronized (o1){
					System.out.println(flag+" o2");
				}
				
			}
		}
	}
}
