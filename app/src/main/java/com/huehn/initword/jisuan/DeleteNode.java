package com.huehn.initword.jisuan;

/**
 * 删除单链表节点
 */
public class DeleteNode {

    public static void main(String[] args){
        DeleteNode deleteNode = new DeleteNode();

        ListNode one = new ListNode(-3);
        ListNode two = new ListNode(5);
        ListNode three = new ListNode(-99);

        one.next = two;
        two.next = three;

        ListNode result = deleteNode.deleteNode2(one, 5);

        while (result != null){
            System.out.println(result);
            result = result.next;
        }

    }

    public ListNode deleteNode(ListNode head, int val) {
        if(head == null || (head.next == null && head.val != val)){
            return head;
        }

        ListNode pointerNode = new ListNode(-1);
        pointerNode.next = head;
        ListNode curNode = pointerNode;
        while(curNode != null && curNode.next != null){

            if(curNode.next.val == val){
                curNode.next = curNode.next.next;
                break;
            }

            curNode = curNode.next;
        }
        return pointerNode.next;

    }

    /**
     * 递归
     * 递归过程中，如果发现next的数有匹配的，直接跳过这个head
     * @param head
     * @param val
     * @return
     */
    public ListNode deleteNode2(ListNode head, int val) {

        if (head == null){
            return null;
        }

        if (head.val == val){
            return head.next;
        }

        head.next = deleteNode2(head.next, val);

        return head;
    }
}
