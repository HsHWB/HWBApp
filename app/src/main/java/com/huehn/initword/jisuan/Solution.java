package com.huehn.initword.jisuan;

import com.nostra13.universalimageloader.utils.L;

///**
// * Definition for singly-linked list.
// * public class ListNode {
// *     int val;
// *     ListNode next;
// *     ListNode(int x) { val = x; }
// * }
// */

/**
 * 单链表加1
 * 用一个 非空 单链表来表示一个非负整数，然后将这个整数加一。
 *
 * 你可以假设这个整数除了 0 本身，没有任何前导的 0。
 *
 * 这个整数的各个数位按照 高位在链表头部、低位在链表尾部 的顺序排列。
 *
 * 示例:
 *
 * 输入: [1,2,3]
 * 输出: [1,2,4]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/plus-one-linked-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public ListNode plusOne(ListNode head) {
        int size = 0;
        ListNode sizeNote = head;
        while (sizeNote != null) {
            size++;
            sizeNote = sizeNote.next;
        }
        if (size == 0) {
            head = new ListNode(1);
            return head;
        }

        ListNode newList = revertList(head);
        add(newList);
        ListNode resultList = revertList(newList);
        return resultList;
    }

    public void add(ListNode listNode){
        ListNode result = listNode;
        if (++result.val > 9){
            result.val = 0;
            if (result.next != null) {
                add(result.next);
            }else {
                result.next = new ListNode(1);
            }
        }
    }

    /**
     * 翻转单向链表
     * @param head
     * @return
     */
    public ListNode revertList(ListNode head){
        ListNode newList = head;
        ListNode node = new ListNode(-1);
        node.next = newList;
        if (node.next != null) {
            ListNode nowNode = node.next;
            ListNode nextNode = nowNode.next;
            if (nextNode == null){
                return node.next;
            }
            while (nextNode != null){
                nowNode.next = nextNode.next;
                nextNode.next = node.next;
                node.next = nextNode;
                nextNode = nowNode.next;
            }
            return node.next;
        }
        return head;
    }

}
