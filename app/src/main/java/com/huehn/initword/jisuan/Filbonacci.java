package com.huehn.initword.jisuan;

/**
 * 斐波那契数列，用递归打印前40个数
 * 思路；递归的话，用于解决重复步骤的问题。如果解决一个问题，里面的算法有重复步骤的话就能用递归
 *
 * 这里fn = f(n - 1) + f(n - 2)
 * f(n - 1),f(n - 2)是重复的。
 *
 * 那么退出递归有两个端。一个是n == 0时，一个是n == n时。
 * 假设退出递归的点设置在n == 40；那么需要一个数组把n = 1， n = 2， n = 3等等时候的数值存下来，当n = n的时候，才知道n -1，n - 2的值
 * 对应是多少。这么做的话还不如用循环去做。
 *
 * 因此如果把return的点设置在0，那么初始传进去的n就是40。算术表达的话，当n >= 2时，一直往下递归。直到n = 2，就开始返过去往n = 40方向递归
 * 求值。
 */
public class Filbonacci {

    public static void main(String[] args) {
        Filbonacci filbonacci = new Filbonacci();
        for (int i = 0; i < 41; i++) {
            System.out.println(filbonacci.filbonacciNum(i));
        }
    }

    public int filbonacciNum(int n){
        int num = -1;
        if (n == 0){
            return 0;
        }

        if (n == 1){
            num = 1;
        }else {
            num = filbonacciNum(n - 1) + filbonacciNum(n - 2);
        }
        return num;
    }

}
