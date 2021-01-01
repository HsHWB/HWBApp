package com.huehn.initword.jisuan;

import java.util.ArrayList;
import java.util.List;

/**
 * 回溯算法
 * 216. 组合总和 III
 * 找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
 *
 * 说明：
 *
 * 所有数字都是正整数。
 * 解集不能包含重复的组合。
 * 示例 1:
 *
 * 输入: k = 3, n = 7
 * 输出: [[1,2,4]]
 * 示例 2:
 *
 * 输入: k = 3, n = 9
 * 输出: [[1,2,6], [1,3,5], [2,3,4]]
 */
public class CombinationSum3 {

    public static void main(String[] args){
        CombinationSum3 combinationSum3 = new CombinationSum3();
        List<List<Integer>> list = combinationSum3.combinationSum3(3, 7);
        for (List<Integer> list1 : list){
            StringBuffer val = new StringBuffer();
            for (Integer integer : list1){
                val.append(integer + ",");
            }
            System.out.println(val);
        }
    }

    /**
     * 假设k=3，则树的深度有3层，每一层的数字都是不同的
     * 第一层节点为1，比如结果是[1,2,3]时，那么后续的[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]都是一样的。
     * 第三层节点的话，只要第三层数字比第二层数字小，都舍弃。因为比第二层数字小的数，在之前第二层数字已经出现。
     * @param k
     * @param n
     * @return
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> resultList = new ArrayList<>();

        for (int headNode = 1; headNode < 10; headNode++){

        }

        return resultList;
    }

}
