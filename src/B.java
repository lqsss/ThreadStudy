
public class B {
	public int a ;
	public static void main(String[] args) {
		System.out.println("这是一条执行路径");
		B b = new B();
		b.m2();
		m1();
	}
	public static void m1(){
		m2();
		m3();
	}
	public static void m2(){}
	public static void m3(){}
}
//这是一条执行路径



