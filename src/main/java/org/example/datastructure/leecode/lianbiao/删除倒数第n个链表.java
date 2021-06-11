package org.example.datastructure.leecode.lianbiao;

import org.example.datastructure.leecode.ListNode;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/820:14
 */
public class 删除倒数第n个链表 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode listNode = new ListNode(0);
        listNode.next = head;
        //定义双指针
        ListNode l = listNode, r = listNode;

        //如果是查找第n个，则直接跑n次；因为是删除，所以得找倒数第n+1个数才能删除，所以跑n+1次
        for (int i = 0; i < n + 1; i++) {
            r = r.next;
        }

        while(r != null) {
            r = r.next;
            l = l.next;
        }
        l.next = l.next.next;

        return  listNode.next;
    }
}
