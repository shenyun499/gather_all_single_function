package org.example.datastructure.leecode.string;

import util.Scanner;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/1419:38
 */
public class 子串 {
    public static void main(String[] args) {
        //输入数据
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int num = 0;
            String s = in.nextLine();
            String t = in.nextLine();
            int sL = s.length();
            int tL = t.length();
            //如果最长串s长度小于最短串，输出最长长度的0，并结束
            if (sL < tL) {
                prin(sL, num);
                return;
            }
            //左边的0开始，每次截取最小串长度的字符串进行与最小串内容比较
            int left = 0, right = tL, moveV = tL;
            while (right < sL) {
                prin(moveV - 1, num);
                String suSt = s.substring(left, right);
                if (t.equals(suSt)) {
                    num++;
                }
                prin(1, num);
                left += moveV;
                right += moveV;
            }
            prin(sL - left, num);
            System.out.println();
        }
    }

    //便于打印字符
    public static void prin(int n, int value) {
        for (int i = 0; i < n; i++) {
            System.out.print(value + " ");
        }
    }

    public static void te() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.next();
            String t = scanner.next();
            int[] dp = new int[s.length()];
            char[] sarr = s.toCharArray();
            char[] tarr = t.toCharArray();
            for(int i=0;i<s.length()-t.length()+1;i++) {
                dp[i] = i==0? 0:dp[i-1];
                if(sarr[i]==tarr[0]) {
                    int j=1;
                    for(;j<tarr.length;j++) {
                        if(sarr[i+j]!=tarr[j])
                            break;
                    }
                    if(j==tarr.length) {
                        dp[i]++;
                    }
                }
            }
            for(int i=0;i<t.length()-1;i++) {
                System.out.print(0+" ");
            }
            for(int i=0;i<s.length()-t.length()+1;i++) {
                System.out.print(dp[i]+" ");
            }
            System.out.println();
        }
    }

}
