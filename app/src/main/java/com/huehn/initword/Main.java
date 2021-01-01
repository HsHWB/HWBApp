package com.huehn.initword;

import android.os.Message;

import com.huehn.initword.activity.SubClass;
import com.huehn.initword.jisuan.ListNode;
import com.huehn.initword.jisuan.Solution;

import java.util.Scanner;
public class Main{

    private Scanner scanner;
    private int nowline = 3;
    private int inputLine = 3;
    private int peopleNum = 0;

    private int[] peopleLikeList;
    private int[] groupList;

    private MoneySum main = new MoneySum();
    private MoneySum[] list = new MoneySum[2];

    public void doMain(){
        list[0] = main;
        System.out.println("main : " + main + "      list 0 :" + list[0]);
        list[0] = null;
        System.out.println("main : " + main + "      list 0 :" + list[0]);
    }

    public static void main(String[] args){

        MoneySum moneySum = new MoneySum();
        System.out.println("main 0 : " + moneySum.getA());
        printResult(moneySum);
        System.out.println("main : " + moneySum.getA());

//        int moneySum = 1;
//        System.out.println("main 0 : " + moneySum);
//        printResult(moneySum);
//        System.out.println("main : " + moneySum);

//        ListNode listNode1 = new ListNode(1);
//        ListNode listNode2 = new ListNode(2);
//        listNode1.next = listNode2;
//        System.out.println("listNode1:" + listNode1 + "      listNode1.next : " + listNode1.next);
//
//        ListNode curNode = listNode1;
//        listNode1 = curNode.next;
//        curNode.next = null;
//        System.out.println("listNode1 : " + listNode1 + "      listNode1.next : " + listNode1.next + "      curNode : " + curNode);
        /*
        Message m = sPool;
                sPool = m.next;
                m.next = null;
         */

        //        Main main = new Main();
//        main.doMain();
//        list[0]
//        Scanner input = main.getScanner();
//        String result = null;
//
//        result = input.nextLine();
//        main.setPeopleNum(Integer.parseInt(result));
//
//        result = input.nextLine();
//        main.setPeopleLikeList(result);
//
//        result = input.nextLine();
//        main.setInputLine(Integer.parseInt(result));
//
//        for(int i = 0; i < main.getInputLine(); i++){
//            result = input.nextLine();
//            main.setGroupList(result);
//        }
//        input.close();
//        ListNode one = new ListNode(0);
//        ListNode two = new ListNode(9);
//        ListNode three = new ListNode(9);
//        one.next = two;
//        two.next = three;
//        Solution solution = new Solution();
//        ListNode result = solution.plusOne(one);
//
//        while (result != null){
//            System.out.println(result);
//            result = result.next;
//        }

        //        System.out.println(solution.plusOne(one));
//        one = two;
//        two = three;
//        System.out.println(one + "      " + two + "      " + three);
    }

    public static void printResult(MoneySum main){
        main.setA(2);
        System.out.println("main 1 : " + main.getA());
    }

    public static void printResult(int main){
        main = 0;
        System.out.println("main 1 : " + main);
    }

    private void setInputLine(int line){
        this.inputLine = line;
    }

    private int getInputLine(){
        return inputLine;
    }

    private void setPeopleLikeList(String value){
        if(value == null || value.length() == 0){
            return;
        }
        String[] result = value.split(" ");
        if(result == null){
            return;
        }
        if(peopleLikeList == null){
            peopleLikeList = new int[result.length];
        }
        try{
            for(int i = 0; i < result.length; i++){
                int num = Integer.parseInt(result[i]);
                peopleLikeList[i] = num;
            }
        }catch(NumberFormatException e){
            e.printStackTrace();
        }

    }
    private void setGroupList(String value){
        if(value == null || value.length() == 0){
            return;
        }
        String[] result = value.split(" ");
        if(result == null){
            return;
        }
        groupList = new int[result.length];
        try{
            for(int i = 0; i < result.length; i++){
                int num = Integer.parseInt(result[i]);
                groupList[i] = num;

            }
            printMatch(groupList);
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
    }

    private void printMatch(int[] value){
        if(value == null || value.length < 3){
            return;
        }

        int l = value[0] - 1;
        int r = value[1] - 1;
        int m = value[2];
        int match = 0;
        if(l < 0 || r < 0 || l >= r || l >= peopleLikeList.length || r >= peopleLikeList.length){
            return;
        }
        for(int i = l; i <= r; i++){
            if(m == peopleLikeList[i]){
                match++;
            }
        }

        System.out.println(match);
    }

    private int getNowLine(){
        return nowline;
    }


    private void setPeopleNum(int peopleNum){
        this.peopleNum = peopleNum;
    }

    private int getPeopleNum(){
        return peopleNum;
    }

    private Scanner getScanner(){
        if(scanner == null){
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
}
