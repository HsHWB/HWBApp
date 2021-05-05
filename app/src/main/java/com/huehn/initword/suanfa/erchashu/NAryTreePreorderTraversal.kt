package com.huehn.initword.suanfa.erchashu

import com.huehn.initword.suanfa.Node

/**
 * https://leetcode-cn.com/problems/n-ary-tree-preorder-traversal/
 * 589. N叉树的前序遍历
 * 给定一个 N 叉树，返回其节点值的前序遍历。
 *
 * 例如，给定一个 3叉树 :
 *
 * 返回其前序遍历: [1,3,5,6,2,4]。
 * 说明: 递归法很简单，你可以使用迭代法完成此题吗?
 *
 * 通过次数65,600提交次数88,461
 */
class NAryTreePreorderTraversal {
    fun preorder(root: Node?): List<Int> {
        var resultList = mutableListOf<Int>()

        return resultList
    }

    fun getChildren(node: Node?, result: List<Int>) {
        if (result == null) {
            return
        }



    }
}