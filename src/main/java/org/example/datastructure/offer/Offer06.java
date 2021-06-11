package org.example.datastructure.offer;

/**
 * 剑指offer06-从尾到头打印链表
 * 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
 *
 * 示例 1：
 * 输入：head = [1,3,2]
 * 输出：[2,3,1]
 *
 * 限制：
 * 0 <= 链表长度 <= 10000
 *
 * 思路：先遍历一遍链表得到长度，第二次遍历将链表值反向插入数组，从尾部插入
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-01-07
 */
public class Offer06 {
    public int[] reversePrint(ListNode head) {
        // 遍历链表得到链表长度
        ListNode l = head;
        int size = 0;
        while (l != null) {
            l = l.next;
            size++;
        }


        int[] arr = new int[size];
        // 第二次遍历将值填入数组
        while (head != null) {
            arr[--size] = head.val;
            head = head.next;
        }
        return arr;
    }
}
