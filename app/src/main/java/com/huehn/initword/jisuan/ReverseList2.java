package com.huehn.initword.jisuan;

/**
 * 92. 反转链表 II
 * 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
 *
 * 说明:
 * 1 ≤ m ≤ n ≤ 链表长度。
 *
 * 示例:
 *
 * 输入: 1->2->3->4->5->NULL, m = 2, n = 4
 * 输出: 1->4->3->2->5->NULL
 */
public class ReverseList2 {

    public static void main(String[] args){
        ReverseList2 reverseList2 = new ReverseList2();

        ListNode one = new ListNode(1);
        ListNode two = new ListNode(2);
        ListNode three = new ListNode(3);
        ListNode four = new ListNode(4);
        ListNode five = new ListNode(5);
        one.next = two;
        two.next = three;
        three.next = four;
        four.next = five;
        ListNode result = reverseList2.reverseBetween(one, 2, 4);
        while (result != null){
            System.out.println(result.toString());
            result = result.next;
        }
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
//        return reverse1(head, m, n);
        return reverse2(head, m, n);
    }

    /**
     * 三个指针原地翻转
     * @return
     */
    public ListNode reverse1(ListNode head, int m, int n){
        if (head == null){
            return null;
        }
        ListNode lastNode = new ListNode(-2);//m前最后一个节点。
        lastNode.next = head;
        ListNode curHead = head;//当前链表第一个元素
        ListNode pointNode = new ListNode(-2);//第三个辅助指针
        pointNode.next = curHead;
        ListNode nextHead = curHead.next;
        int begin = 0;
        while (nextHead != null && curHead != null){
            begin++;
            if (begin < m){
                lastNode = curHead;
                curHead = curHead.next;
                nextHead = curHead.next;
                pointNode.next = curHead;
                continue;
            }else if (begin >= n) {
                break;
            }

            /**
             * curHead.next = nextHead.next   2指向4
             *            cur         cur.next(nextHead)     nextHead.next
             * 1    ->      2      ->      3             ->      4      ->      5
             *
             *            cur         cur.next(nextHead)     nextHead.next
             * 1    ->      2            3             ->      4      ->      5
             *              ↓----------------------------------↑
             */
            curHead.next = nextHead.next;

            /**
             * 上一步
             * lastNode         cur         cur.next(nextHead)     nextHead.next
             *      1    ->      2            3             ->      4      ->      5
             *                   ↓----------------------------------↑
             *
             * 这一步
             *     ↑--------------------------↓
             * lastNode         cur         cur.next(nextHead)     nextHead.next
             *      1          2            3             ->      4      ->      5
             *                   ↓--------------------------------↑
             * 也就成了
             * 1     ->     3      ->      4      ->      5
             *                             ↑
             *                             2
             */
            lastNode.next = nextHead;

            /**
             * 上一步
             * 1     ->     3      ->      4      ->      5
             *                             ↑
             *                             2
             *
             * 这一步nextHead.next = pointNode.next;
             *           cur.next
             * 1     ->     3               4      ->      5
             *              ↓               ↑
             *              ↓-------------->2(cur)
             */
            nextHead.next = pointNode.next;

            pointNode.next = nextHead;
            nextHead = curHead.next;
        }
        if (m == 1){
            return lastNode.next;
        }else {
            return head;
        }
    }

    /**
     * 递归翻转
     *
     * https://zhuanlan.zhihu.com/p/86745433?utm_source=ZHShareTargetIDMore
     */
    private int begin = 0;
    public ListNode reverse2(ListNode head, int m, int n){
        if (head == null || head.next == null){
            return head;
        }
        if (begin == n || begin == n - 1){
            return head;
        }
        begin++;
        ListNode lastHead = reverse2(head.next, m, n);
        head.next.next = head;
        head.next = null;
        if (begin == m){
            head.next = lastHead;
        }
        begin--;
        return lastHead;
    }

}
