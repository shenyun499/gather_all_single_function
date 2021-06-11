package org.example.datastructure.com.design.abstractfactory;

/**
 * @Description
 * @Author xuexue
 * @Date 2019/6/217:05
 */
public class AbstractFactory {
    public static void main(String[] args) {
        //选择土系列中，找到猫
        AnimalFactory lowFactory = new LowFactory();
        lowFactory.createCat().name();

        //选择在宠物系列中找到狗
        AnimalFactory petFactory = new PetFactory();
        petFactory.createDog().name();
    }
}

//接口Dog、Cat、Pig
interface Dog {
    void name();
}
interface Cat {
    void name();
}
interface Pig {
    void name();
}

//接口Dog、Cat、Pig的实现类（土、宠物）
class LowDog implements Dog {
    @Override
    public void name() {
        System.out.println("我是土狗");
    }
}
class PetDog implements Dog {
    @Override
    public void name() {
        System.out.println("我是宠物狗");
    }
}

class LowCat implements Cat {
    @Override
    public void name() {
        System.out.println("我是土猫");
    }
}
class PetCat implements Cat {
    @Override
    public void name() {
        System.out.println("我是宠物猫");
    }
}

class LowPig implements Pig {
    @Override
    public void name() {
        System.out.println("我是土猪");
    }
}
class PetPig implements Pig {
    @Override
    public void name() {
        System.out.println("我是宠物猪");
    }
}

//工厂
interface AnimalFactory {
    Dog createDog();
    Cat createCat();
    Pig createPig();
}
//土工厂实现类
class LowFactory implements AnimalFactory {
    @Override
    public Dog createDog() {
        return new LowDog();
    }

    @Override
    public Cat createCat() {
        return new LowCat();
    }

    @Override
    public Pig createPig() {
        return new LowPig();
    }
}

//土工厂实现类
class PetFactory implements AnimalFactory {
    @Override
    public Dog createDog() {
        return new PetDog();
    }

    @Override
    public Cat createCat() {
        return new PetCat();
    }

    @Override
    public Pig createPig() {
        return new PetPig();
    }
}