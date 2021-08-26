package fanxing;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther huangzhixue
 * @data 2021/8/6 11:34 上午
 * @Description
 */
public class Fanxing {
        /**
         * 引用类型和实例是两码事，这里我注意到了。下面也给出一点自己的总结。
         * 1、List<? super T> 引用类型是T和T的父类(一个范围不能确定具体，不能确定上限（但是可以认为Object是上限），只能确定下限)，范围中：T是下限。
         * 所以add，添加元素实例必须是下限T或者下限T的子类。get时只能用上限引用类型去接。
         * 下限是T，这里就是下界
         * 引用类型是T-？这个范围，元素必须在这个范围内，固需要的元素实例是T范围或者是T范围的子类。
         *
         * 2、List<? extends T>引用类型是T和T的子类（一个范围不能确定具体，能确定上限是T，不能确定下限）。
         * 所以添加元素时只能添加null，因为我不能确定下限呀。但是get时也能用上限的T引用类型去接。
         * 上限是T，这里就是上界
         * 引用类型是？-T这个范围，元素必须在这个范围内，固需要的元素实例是？范围或者是？范围的子类，可惜这个不确定，固只能添加null。
         *
         * 就此，范型上下界吃透
         *
         * ps：上限下限我这里指的是引用类型而不是添加元素实例。get只能用上限接，add只能add下限或者下限的子类
         */
    public static void main(String[] args) {

        List<? super Animal> listA = new ArrayList<>();
        List<? extends Plant> listB = new ArrayList<>();

        //listA.add(new Biological());
        listA.add(new Animal());
        listA.add(new Dog());
        //listA.add(new Flower());
        Object object = listA.get(0);
        Biological biological = (Biological)object;

        //listB.add(new Plant());
        //listB.add(new Flower());
        listB.add(null);
        Plant plant = listB.get(0);


    }



    /**
     * 生物
     */
    static class Biological{
    }

    /**
     * 动物
     */
    static class Animal extends Biological{
    }

    /**
     * 植物
     */
    static class Plant extends Biological{
    }


    static class Dog extends Animal{
    }

    static class Cat extends Animal{
    }

    static class Flower extends Plant{
    }

    static class Tree extends Plant{
    }

}

