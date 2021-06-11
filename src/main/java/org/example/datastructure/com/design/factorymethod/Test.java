package org.example.datastructure.com.design.factorymethod;

/**
 * @Description
 * 测试类
 * @Author
 * @Date 2019/6/121:41
 */
public class Test {
    public static void main(String[] args) {
        //测试猫
        AnimalFactory catFactory = new CatFactory();
        Animal cat = catFactory.getAnimal();
        cat.name();
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

//Cat的工厂实现类
class CatFactory implements AnimalFactory {
    @Override
    public Animal getAnimal() {
        return new Cat();
    }
}

//Dog的工厂实现类
class DogFactory implements AnimalFactory {
    @Override
    public Animal getAnimal() {
        return new Dog();
    }
}