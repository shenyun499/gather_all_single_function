package org.example.datastructure.leecode;
import util.Scanner;
public class 求一个数的m开n次方的结果 {
    //x^n无限逼近于m就行，所以，每次对m进行二分，然后判断是不是无限接近
        static double near = 10E-12;
        public static void main(String[] args) {
            Scanner scan = new Scanner(System.in);
            //数据录入m的开n次方
            double m = scan.nextDouble();
            int n = scan.nextInt();
            //作除法，然后每次除2进行判断，直到误差小于near时退出。
            double left = 0, right = m;
            double mu = (left + right) / 2.0;
            double num = Math.pow(mu, n);
            while (Math.abs(m - num) > near) {
                mu = (left + right) / 2.0;
                //再次相乘
                num = Math.pow(mu, n);
                //结果要大，往左边走
                if (num > m) {
                    right = mu;
                } else {
                    left = mu;
                }
            }
            System.out.printf(String.format("%.12f", mu));
        }
//    static double near = 10E-14;
//    public static void main(String[] args) {
//        Scanner scan = new Scanner(System.in);
//        //数据录入m的开n次方
//        int m = scan.nextInt();
//        int n = scan.nextInt();
//        //作除法，然后每次除2进行判断，直到误差小于near时退出。
//        double left = 0, right = m;
//        double mu = (left + right) / 2.0;
//        double num = Math.pow(mu, n);
//        while (Math.abs(m - num) > near) {
//            //结果要大，往左边走
//            if (num > m) {
//                right = mu;
//            } else {
//                left = mu;
//            }
//            mu = (left + right) / 2.0;
//            //再次相乘
//            num = Math.pow(mu, n);
//        }
//        System.out.printf(String.format("%.12f", mu));
//    }
/*    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        double m=sc.nextDouble();
        int n=sc.nextInt();
        if(n==1){
            System.out.println(String.format("%.12f",m));
            return;
        }
        double l,r;
        l=1;
        r=m;
        double mid=(l+r)/2;
        while(r-l>0.5*10e-14){
            mid=(l+r)/2;
            double num=Math.pow(mid,n);
            if(num>m){
                r=mid;
            }else if(num<m){
                l=mid;
            }else{

                break;
            }
        }
        System.out.println(String.format("%.12f",mid));
    }*/
}