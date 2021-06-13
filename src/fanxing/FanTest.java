package fanxing;

import java.util.ArrayList;

/**
 * 泛型 上下界
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-04-08
 */
public class FanTest {

    public static void main(String[] args) {
//        ArrayList<? super Parent> arrayList = new ArrayList<>();
//        arrayList.add(new ParentImpl());
//        arrayList.get(0);
//        ArrayList<? extends Parent> arrayList2 = new ArrayList<>();
//        //arrayList2.add(new ParentImpl());
//        arrayList2.get(0);
        Double aDouble = new Double(100.0);
        Double aDouble2 = new Double(100.0);
        System.out.println(aDouble == aDouble2);

        System.out.println(new Integer(40) == new Integer(40));
        Integer i = new Integer(40);
        Integer a = 40;
        Integer b = 40;
        Double c = 100.0;
        Double d = 100.0;
        System.out.println(a == i);
        System.out.println(a == b);
        System.out.println(c == d);
    }
}
