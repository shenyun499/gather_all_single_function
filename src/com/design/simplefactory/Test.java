package com.design.simplefactory;

/**
 * @Description
 * 测试类
 * @Author
 * @Date 2019/6/121:41
 */
public class Test {
    public static void main(String[] args) {
        /*Animal dog = SimpleFactory.getDog();
        Animal cat = SimpleFactory.getCat();
        dog.name();
        cat.name();*/

        //优化
        Animal animal = SimpleFactory.getAnimal("a");
        animal.name();
    }

}

class Dog implements Animal {

    @Override
    public void name() {
        System.out.println("我是狗");
    }
}

class Cat implements Animal {

    @Override
    public void name() {
        System.out.println("我是猫");
    }
}

//简单工厂类
class SimpleFactory {

   /* public static Animal getCat() {
        return new Cat();
    }

    public static Animal getDog() {
        return new Dog();
    }*/

    //优化
    public static Animal getAnimal(String name) {
        if (name.equalsIgnoreCase("dog")) {
            return new Dog();
        } else if (name.equalsIgnoreCase("cat")) {
            return new Cat();
        }
        return null;
    }
}

