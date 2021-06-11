package org.example.datastructure.leecode;


/**
 * @Description
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 *
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例：
 *
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 *
 * @Author xuexue
 * @Date 2019/12/11 19:06
 */
public class 两数相加 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //新的链表，带头指针为0
        ListNode newList = new ListNode(0);

        //进位数 比如9+9=18 求进位数就是18/10=1。当然，实际还要加上原来的进位数 就是(9+9+curr)/10
        int carry = 0;
        ListNode p = l1, q = l2, newL = newList;

        while (p != null || q != null) {
            //得到链表值
            int x = p == null ? 0 : p.val;
            int y = q == null ? 0 : q.val;

            //计算两数值
            int sum = x + y + carry;

            //计算进位数
            carry = sum / 10;

            //根据值生成新的节点
            newL.next = new ListNode(sum % 10);

            //让节点指向新的节点
            newL = newL.next;

            //让两个链表重新链接
            if (p != null) {
                p = p.next;
            }
            if (q != null) {
                q = q.next;
            }
        }

        if (carry > 0) {
            newL.next = new ListNode(carry);
        }

        return newList.next;
    }

    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        //声明出备份变量，创建需要返回的新链表
        ListNode newListNode = new ListNode(0);
        ListNode p = l1, q = l2, curr = newListNode;

        //声明变量记录进位
        int carry = 0;

        //同时对两个链表进行遍历
        while (p != null || q != null) {
            //获取链表的值，如果不为null则取值，否则则取0。三目解决
            int x = p != null ? p.val : 0;
            int y = q != null ? q.val : 0;

            //计算总值
            int sum = x + y +carry;

            //根据总值计算进位 /
            carry = sum / 10;

            //根据总值赋值链表 %
            curr.next = new ListNode(sum % 10);

            //改变当前新链表的指向
            curr = curr.next;

            //改变两条链表的指向
            if (p != null) {
                p = p.next;
            }
            if (q != null) {
                q = q.next;
            }
        }
        //判断进位是否还有
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return newListNode;
    }
}


