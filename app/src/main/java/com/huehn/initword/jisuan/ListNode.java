package com.huehn.initword.jisuan;

public class ListNode {
      public int val;
      public ListNode next;
      public ListNode(int x) { val = x; }

      @Override
      public String toString() {
            int nextVal = -2;
            if(next != null){
                nextVal = next.val;
            }
            return "ListNode{" +
                    "val=" + val + "      nextVal = " + nextVal +
                    '}';
      }
}
