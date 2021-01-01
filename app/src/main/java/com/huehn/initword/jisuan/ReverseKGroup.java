package com.huehn.initword.jisuan;

/**
 * 25. K 个一组翻转链表
 * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 *
 * k 是一个正整数，它的值小于或等于链表的长度。
 *
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 *
 *
 *
 * 示例：
 *
 * 给你这个链表：1->2->3->4->5
 *
 * 当 k = 2 时，应当返回: 2->1->4->3->5
 *
 * 当 k = 3 时，应当返回: 3->2->1->4->5
 *
 *
 *
 * 说明：
 *
 * 你的算法只能使用常数的额外空间。
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 */
public class ReverseKGroup {

    public static void main(String[] args){
        ReverseKGroup reverseKGroup = new ReverseKGroup();

        ListNode one = new ListNode(1);
        ListNode two = new ListNode(2);
        ListNode three = new ListNode(3);
        ListNode four = new ListNode(3);
        ListNode five = new ListNode(3);

        one.next = two;
        two.next = three;
        three.next = four;
        four.next = five;

        ListNode result = reverseKGroup.reverseKGroup(one, 2);

        while (result != null){
            System.out.println(result);
            result = result.next;
        }

    }

    public ListNode reverseKGroup(ListNode head, int k) {

        ListNode startNode = new ListNode(-1);//移动指针
        startNode.next = head;
        ListNode pointerNode = startNode;//记录表头，不移动
        ListNode lastNode = startNode;//记录完成k翻转之后的最后一个节点

        pointerNode = pointerNode.next;
        int nowK = 0;

        if (k == 1){
            return head;
        }

        while (pointerNode != null){
            nowK++;
            if (nowK == k){
//                 pointerNode.next = reverse(lastNode.next);
                lastNode.next = lastNode.next.next;
                pointerNode = lastNode;
                nowK = 0;
            }
            pointerNode = pointerNode.next;
        }
        return startNode.next;
    }

//    public ListNode reverse(ListNode head){
//
//    }
}
