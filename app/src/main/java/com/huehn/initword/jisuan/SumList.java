package com.huehn.initword.jisuan;

/**
 * 面试题 02.05. 链表求和
 * 给定两个用链表表示的整数，每个节点包含一个数位。
 *
 * 这些数位是反向存放的，也就是个位排在链表首部。
 *
 * 编写函数对这两个整数求和，并用链表形式返回结果。
 *
 *
 *
 * 示例：
 *
 * 输入：(7 -> 1 -> 6) + (5 -> 9 -> 2)，即617 + 295
 * 输出：2 -> 1 -> 9，即912
 * 进阶：假设这些数位是正向存放的，请再做一遍。
 *
 * 示例：
 *
 * 输入：(6 -> 1 -> 7) + (2 -> 9 -> 5)，即617 + 295
 * 输出：9 -> 1 -> 2，即912
 */
public class SumList {

    public static void main(String[] args){
        SumList sumList = new SumList();

        ListNode one = new ListNode(1);
        ListNode two = new ListNode(8);
        ListNode three = new ListNode(3);
        one.next = two;
        two.next = three;

        ListNode one1 = new ListNode(7);
        ListNode two2 = new ListNode(1);
//        ListNode three3 = new ListNode(2);
        one1.next = two2;
//        two2.next = three3;

        ListNode result = sumList.addTwoNumbers2(one, one1);

        while (result != null){
            System.out.println(result);
            result = result.next;
        }

    }

    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
//        int l1Size = listSize(l1);
//        int l2Size = listSize(l2);
//        int length = l1Size > l2Size ? l1Size : l2Size;//取最大的长度
//        int nowSize = 0;
//        ListNode result = l1Size > l2Size ? l1 : l2;//用最大长度的单链表作为基础链表。另外一个链表直接往上加。
//
//        while (nowSize < length){
//            nowSize++;
//
//        }
        ListNode resultNode = l1;
        if (l2 == null){
            return l1;
        }
        if (l1 == null){
            return l2;
        }
        while (l1 != null){ //l1的长度n次
            if (l2 != null) {
                l1.val += l2.val;
                if (l1.val > 9) {
                    l1.val = l1.val - 10;
                    if (l1.next != null) {
                        l1.next.val++;
                    } else if (l2.next != null) {
                        l2.next.val++;
                    } else {
                        l1.next = new ListNode(1);
                    }
                }
                l2 = l2.next;
            }else {
                if (l1.val > 9){
                    l1.val = l1.val - 10;
                    if (l1.next != null){
                        l1.next.val++;
                    }else {
                        l1.next = new ListNode(1);
                    }
                }
            }
            if (l1.next != null){
                l1 = l1.next;
            }else {
                break;
            }
        }
        if (l2 != null){
            l1.next = l2;
            while (l2 != null){//l2的长度m - n次。
                if (l2.val > 9){
                    l2.val = l2.val - 10;
                    if (l2.next != null){
                        l2.next.val++;
                    }else {
                        l2.next = new ListNode(1);
                    }
                }
                l2 = l2.next;
            }
        }
        return resultNode;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int l1Size = listSize(l1);
        int l2Size = listSize(l2);
        int length = l1Size > l2Size ? l1Size : l2Size;//取最大的长度
        int nowSize = 0;
        ListNode resultNode = new ListNode(0);
        ListNode backNode = resultNode;
        //(7 -> 1 -> 6) + (5 -> 9 -> 2)
        //O(n)
        if (l1Size < l2Size){
            l1 = addZero(l1, l2Size - l1Size, l1Size);
        }else if (l1Size > l2Size){
            l2 = addZero(l2, l1Size - l2Size, l2Size);
        }
        while (nowSize < length){
            int result = l1.val + l2.val;
            if (result > 9){
                result = result - 10;
                if (l1.next != null) {
                    l1.next.val++;
                }else if (l2.next != null){
                    l2.next.val++;
                }else {
                    resultNode.val = result;
                    resultNode.next = new ListNode(1);
                    return backNode;
                }
            }
            nowSize++;
            resultNode.val = result;
            l1 = l1.next;
            l2 = l2.next;
            if (nowSize < length){
                resultNode.next = new ListNode(0);
                resultNode = resultNode.next;
            }
        }

        return backNode;
    }

    private ListNode addZero(ListNode listNode, int n, int listSize){
        ListNode head = listNode;
        if (listNode == null){
            listNode = new ListNode(0);
        }
        while (listNode.next != null){
            listNode = listNode.next;
        }
        for (int i = 0; i < n; i++){
            listNode.next = new ListNode(0);
            listNode = listNode.next;
        }
        return head;
    }

    private int listSize(ListNode listNode){
        int size = 0;
        if (listNode == null){
            return size;
        }

        while (listNode != null){
            size++;
            listNode = listNode.next;
        }
        return size;
    }


}
