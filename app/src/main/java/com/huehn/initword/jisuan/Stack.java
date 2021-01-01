package com.huehn.initword.jisuan;

import java.util.LinkedList;

public class Stack<T> {

    public LinkedList<T> linkedList;

    public Stack() {
        linkedList = new LinkedList<>();
    }

    public void put(T t){
        linkedList.offerLast(t);
    }

    public T poll(){
        return linkedList.pollLast();
    }

}
//class CQueue {
//    Deque<String> stack1;
//    Deque<String> stack2;
//
//    public CQueue() {
//        stack1 = new LinkedList<String>();
//        stack2 = new LinkedList<Integer>();
//    }
//
//    public void appendTail(int value) {
//        stack1.push(value);
//    }
//
//    public int deleteHead() {
//        // 如果第二个栈为空
//        if (stack2.isEmpty()) {
//            while (!stack1.isEmpty()) {
//                stack2.push(stack1.pop());
//            }
//        }
//        if (stack2.isEmpty()) {
//            return -1;
//        } else {
//            int deleteItem = stack2.pop();
//            return deleteItem;
//        }
//    }
//}
