package com.bter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class ProxyTest {
	
	public static void main(String a[])
	{
		BusCar busCar = new BusCar();
		Class<? extends BusCar> _class =busCar.getClass();
		CarInvocationHandler carInvocationHandler = new CarInvocationHandler(busCar);
		Car car =(Car) Proxy.newProxyInstance(_class.getClassLoader(), _class.getInterfaces(), carInvocationHandler);
		car.getCarName("去不一态度太");
	}

	public interface Car {
		 String getCarName(String name);
	}
	
	public  static class BusCar implements Car{
		public String getCarName(String name) {
			System.out.println(name);
			return name;
		}
	}
	
	public static class CarInvocationHandler  implements InvocationHandler 
	{
		private Object ob;
		
		public	CarInvocationHandler(Object src)
		{
			this.ob=src;
		}
		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			System.out.println("invoke the method before"+method.getName());
			Object _result = method.invoke(ob, args);
			System.out.println("invoke the method after"+method.getName());
			return _result;
		}
		
	}
}
