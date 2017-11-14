package ThreadCollaboration;


/**
 * wait+notifyʵ���߳�Э�� ��ģ�ͣ�һ�Ҳ���������Ա�������ߣ�����ȴ���ʦ�������ߣ����һ���ˣ��ſ��Զ���
 * 
 * @author liqiushi
 * 
 */

class Meal{
	private int count;
	private int orderId;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public String toString(){
		return orderId+ " ";
	}
}
/**
 * ������
 * 
 * @author liqiushi
 * 
 */

class Waiter implements Runnable {
	private Restaurant restaurant = null;

	public Waiter(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public void run() {		
		try {
			while(!Thread.interrupted()){
				while(restaurant.getMeal() == null){
					this.wait(); //�ȴ�chef
				}
				System.out.println("�������Ѿ����߲ˣ�"+ restaurant.getMeal());
			}
		} catch (Exception e) {
			
		}
	
	}

}

public class Restaurant {
	private Waiter waiter = new Waiter(this);
	private Meal meal = null;

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public Meal getMeal() {
		return meal;
	}

	public static void main(String[] args) {

	}
}
