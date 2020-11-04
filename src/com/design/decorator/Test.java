package com.design.decorator;

public class Test {
	public static void main(String[] args) {
		Car runCar = new RunCar();
		runCar.show();
		System.out.println("-----------------------原始车只会跑");
		
		CarDecorator flyCar = new FlyCarDecorator(runCar);
		flyCar.show();
		System.out.println("-----------------------原始车装饰添加功能飞");
		
		CarDecorator swimCar = new SwimCarDecorator(runCar);
		swimCar.show();
		System.out.println("-----------------------原始车装饰添加功能游");
		
		CarDecorator swimFlyCar = new SwimCarDecorator(flyCar);
		swimFlyCar.show();
		System.out.println("-----------------------会飞车装饰添加功能游");
		
	}

}
