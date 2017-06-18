package com.bter;


public class AppTest {

	interface A {
		void methodA();
	}
	interface B
	{
		void methodB();
	}
	interface C extends A,B
	{
		void methodC();
	}
	
	static class D implements C
	{

		@Override
		public void methodA() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void methodB() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void methodC() {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static void main(String a[])
	{
		D d = new D();
		d.methodA();
	}
}
