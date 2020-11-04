package leecode.lianbiao;

import leecode.ListNode;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/820:10
 */
public class 合并两条有序链表 {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode newL = new ListNode(0);
        ListNode l = l1, r = l2, newLs = newL;
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
        return newL.next;
    }
}
