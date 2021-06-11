package org.example.datastructure.leecode.lianbiao;

import org.example.datastructure.leecode.ListNode;
import org.junit.Test;

import util.Scanner;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/1014:37
 */
public class 两两交换相邻的链表值 {
    @Test
    public void swNode() {
        //初始测试数据
        ListNode node = new ListNode(1);
        ListNode initNode = node;
        for (int i = 2; i < 10; i++) {
            initNode.next = new ListNode(i);
            initNode = initNode.next;
        }
        //测试
        ListNode node1 = swNode(node);
        while (node1 != null) {
            System.out.println(node1.val);
            node1 = node1.next;
        }
    }

    public static ListNode swNode(ListNode listNode) {
        int n = 0;
        //特判
        Scanner scan = new Scanner(System.in);
        scan.hasNextInt();
        if (listNode == null) {
            int[] arr = new int[n];
            return null;
        } else if (listNode.next == null) {
            return listNode;
        }
        //双指针进行交换
        ListNode l = listNode, r = listNode.next;
        ListNode head = r;
        ListNode temp = null;
        while (r != null) {
            //交换
            l.next = r.next;
            r.next = l;
            //交换位置之后进行链接操作
            if (temp != null) {
                temp.next = r;
            }
            //交换位置
            temp = r;
            r = l;
            l = temp;
            //移动指针
            for (int i = 0; i < 2 && r != null; i++) {
                if (i == 1) {
                    temp = l;
                }
                r = r.next;
                l = l.next;
            }
        }
        return head;
    }
}
