package org.example.datastructure.leecode.lianbiao;

import org.example.datastructure.leecode.ListNode;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/820:02
 */
public class 合并k条有序链表 {
    //合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        ListNode newL = new ListNode(-1);
        ListNode l = lists[0], r = null, newLs = newL;
        for (int i = 1; i < lists.length; i++) {
            r = lists[i];
            while (l != null && r != null) {
                if (l.val <= r.val) {
                    newLs.next = l;
                    l = l.next;
                } else {
                    newLs.next = r;
                    r = r.next;
                }
                newLs = newLs.next;
            }
            newLs.next = l == null ? r : l;
            newLs = newL;
            l = newLs.next;
        }
        return lists.length == 1 ? l : newL.next;
    }
}
