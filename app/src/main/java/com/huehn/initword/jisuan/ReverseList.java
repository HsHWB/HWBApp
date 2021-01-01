package com.huehn.initword.jisuan;

import java.util.LinkedList;

/**
 * 反向单列表
 */
public class ReverseList {
    /**
     * 用三个指针原地反向
     * @param head
     * @return
     */
    public ListNode reverseList1(ListNode head) {
        if(head == null){
            return null;
        }

        ListNode pointerNode = new ListNode(-1);
        pointerNode.next = head;

        ListNode curHead = pointerNode.next;
        ListNode nextNode = curHead.next;

        while(nextNode != null){
            curHead.next = nextNode.next;
            nextNode.next = pointerNode.next;
            pointerNode.next = nextNode;
            nextNode = curHead.next;
        }
        return pointerNode.next;
    }

    /**
     * 用递归法反向单向链表
     * 1->2->3->4->5->null
     *
     * 当指针head指向4时，下一次递归就是head.next指向5
     *                                            head             head.next
     * 1    ->      2       ->      3       ->      4       ->      5       ->      null
     *
     *
     * head.next.next = head;此时head.next是4的下一个指针5，head.next.next是5的下一个指针null。
     * 将head也就是4赋值给head.next.next，此时5,4之间就是双向链表
     *                                                       head.next      head.next.next (head)
     * 1    ->      2       ->      3        （此处无关系）        5       <->           4
     *                                  ↓---------------------------------3指向4--------↑
     *
     * 也就是这样
     *                                              head.next.next (head)         head.next
     * 1    ->      2       ->      3           ->         4           <->         5
     *
     *
     *
     * head.next = null;  head.next指4的next，4不需要再指向5了，因此置为null，4,5之间的双链表关系变成5指向4的关系。
     *                                              head.next.next (head)         head.next
     * 1    ->      2       ->      3           ->         4           <-         5
     *
     *
     * return last;//继续把5向上返回。此时head由4变为3。因为在代码
     * ListNode last = reverseList2(head.next)。中，head.next为4的话，head就是3。递归的最深那部的方法，head局部变量已经全部出栈，不会保留数据，此时的head就是3。
     *                              head                 head.next
     * 1    ->      2       ->      3           ->         4           <-         5
     *
     * head.next.next = head：
     * head.next.next就是4的下一个。原本是5，但是经过递归的最深处变换了一次之后，4的next不再指向5，其实是指向null。此时的4分别被3和5指向。
     * 当调用了head.next.next = head之后，4的next指向3，3跟4成为双链表
     *                              head                 head.next
     * 1    ->      2       ->      3           <->         4           <-         5
     *
     * head.next = null;  head是3，head.next原本指向4，现在指向null，3跟4的双链表关系结束。
     *                              head                 head.next
     * 1    ->      2       ->      3           <-        4           <-         5
     * @param head
     * @return
     */
    public ListNode reverseList2(ListNode head){
        if (head == null || head.next == null){//空链表，或者是最后一个元素，则返回这个元素
            return head;
        }
        ListNode last = reverseList2(head.next);//这个递归，直到上面的head为null或者head.next为null才会结束
        head.next.next = head;
        head.next = null;
        return last;//last永远指向5，不变。
    }



}

