package com.example.leetcode_ex

import java.util.LinkedList

/**
 * 二叉树
 * 从左和右搜集信息，然后汇总
 *
 * 中旭遍历搜索二叉树，结果是有序数组
 *
 * 深度优先和广度优先：单棵树没有太大差别
 * 深度优先：空间复杂更低
 * 广度优先：遍历层级和最短路径
 *
 * 深度优先DFS模版：
 * void dfs(TreeNode root) {
 *     if (root == null) {
 *         return;
 *     }
 *     dfs(root.left);
 *     dfs(root.right);
 * }
 *
 * 广度优先BFS模版：
 * 如果需要知道每一层，增加一个size，记录遍历次数
 * void bfs(TreeNode root) {
 *     Queue<TreeNode> queue = new ArrayDeque<>();
 *     queue.add(root);
 *     while (!queue.isEmpty()) {
 *         int n = queue.size();
 *         for (int i = 0; i < n; i++) {
 *             // 变量 i 无实际意义，只是为了循环 n 次
 *             TreeNode node = queue.poll();
 *             if (node.left != null) {
 *                 queue.add(node.left);
 *             }
 *             if (node.right != null) {
 *                 queue.add(node.right);
 *             }
 *         }
 *     }
 * }
 *
 * 自顶向下的DFS的模版
 *
 */
class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

fun main(args: Array<String>) {
    val t = TreeNode(1)
    t.left = TreeNode(2)
        .apply {
            left = TreeNode(4)
        }
    t.right = TreeNode(3)
        .apply {
            right = TreeNode(5)
        }
    zigzagLevelOrder(t)
}


/**
 * 给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
 * 输入：p = [1,2,3], q = [1,2,3]
 * 输出：true
 *
 * 解题：逐层比较即可，先中后都可以
 */
fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
    if (p == null && q == null) return true
    if ((p == null) xor (q != null)) return false
    if (p!!.`val` != q!!.`val`) return false
    return isSameTree(p.left, q.left) && isSameTree(p.right, q.right)
}

/**
 * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
 * 输入：root = [1,2,2,3,4,4,3]
 * 输出：true
 *
 * 解题：将根节点作为两个，分别传进入比较
 */
fun isSymmetric(root: TreeNode?): Boolean {
    if (root == null || (root.left == null && root.right == null)) return true
    return isSymmetric(root.left, root.right)
}

fun isSymmetric(root1: TreeNode?, root2: TreeNode?): Boolean {
    if (root1 == null && root2 == null) return true
    if ((root1 == null) xor (root2 == null)) return false
    if (root1!!.`val` != root2!!.`val`) return false
    return isSymmetric(root1.left, root2.right) && isSymmetric(root1.right, root2.left)
}

/**
 * 给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：[[3],[9,20],[15,7]]
 *
 * 解题
 * 利用放入队列，然后取出来
 * 放到队列中的时候，要先放左再放右，注意顺序
 * 存在多少个在队列中，就是要循环遍历的次数
 */
fun levelOrder(root: TreeNode?): List<List<Int>> {
    val all = mutableListOf<List<Int>>()
    if (root != null) {
        val queue = LinkedList<TreeNode>()
        queue.push(root)
        while (queue.isNotEmpty()) {
            val list = mutableListOf<Int>()
            val size = queue.size
            for (i in 0..size - 1) {
                val n = queue.poll()
                list.add(n.`val`)
                if (n.left != null) {
                    queue.offer(n.left)
                }
                if (n.right != null) {
                    queue.offer(n.right)
                }
            }
            all.add(list)
        }
    }
    return all
}

/**
 * 利用深度先序遍历，头左右，将list传进入，并标记自己是哪一层，每一层自己继续往后添加
 */
//fun levelOrder(root: TreeNode?): List<List<Int>> {
//    val list = mutableListOf<MutableList<Int>>()
//
//    println(root,0,list)
//    return list
//}
//fun println(root:TreeNode?, i:Int, list:MutableList<MutableList<Int>>){
//    if (null == root)
//        return
//
//    if(list.size <= i){
//        list.add(mutableListOf<Int>())
//    }
//    list[i].add(root.`val`)
//    println(root.left,i+1,list)
//    println(root.right,i+1,list)
//}

fun isValidBST(root: TreeNode?): Boolean {
    if (root == null) return true
    return isValidBST2(root)?.isBT ?: false
}

/**
 * 给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：[[3],[20,9],[15,7]]
 *
 * 解题 后序遍历  或者  广度优先
 */
fun zigzagLevelOrder(root: TreeNode?): List<List<Int>> {
    val list = mutableListOf<List<Int>>()
    if (root == null) return list

    val queue = LinkedList<TreeNode>()
    queue.offer(root)
    var isFlag = false
    while (queue.isNotEmpty()) {
    }
    return list
}

/**
 * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 平衡 二叉搜索树。
 * 输入：nums = [-10,-3,0,5,9]
 * 输出：[0,-3,9,-10,null,5]
 *
 * 解题
 * 中序遍历，可以按中间点中间（left + right）/2 或者 （left + right + 1）/2
 */
fun sortedArrayToBST(nums: IntArray): TreeNode? {
    if (nums.size == 0) return null
    if (nums.size == 1) return TreeNode(nums[0])
    return sorted(nums, 0, nums.size - 1)
}

fun sorted(nums: IntArray, left: Int, right: Int): TreeNode? {
    if (left > right) return null
    if (left == right) return TreeNode(nums[left])

    val mid = (left + right) / 2
    val l = sorted(nums, left, mid - 1)
    val r = sorted(nums, mid + 1, right)
    val t = TreeNode(nums[mid])
    t.left = l
    t.right = r
    return t
}

/**
 * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。
 * 判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum
 * 如果存在，返回 true ；否则，返回 false
 *
 * 解题
 * 将自己减去，然后将剩余值交给下面
 * 如果最后一个子节点，即两边都为空，则判断当前值与给的目标值是否一致
 */
fun hasPathSum(root: TreeNode?, targetSum: Int): Boolean {
    if (root == null) return false
    if (root.left == null && root.right == null) return targetSum == root.`val`
    return hasPathSum(root.left, targetSum - root.`val`)
            || hasPathSum(root.right, targetSum - root.`val`)
}

fun isValidBST2(root: TreeNode?): Info? {
    if (root == null) return null

    //   5
    // 4   6
    //    3 7

    val l = isValidBST2(root.left)
    println("val = " + root.`val`)
    val r = isValidBST2(root.right)

    var flag = true
    if (l != null && l.max >= root.`val`) {
        flag = false
    }
    if (r != null && root.`val` >= r.min) {
        flag = false
    }
//    println("val = " + root.`val`)
    var max = root.`val`
    if (l != null) {
        if (!l.isBT) {
            flag = false
        }
    }
    var min = root.`val`
    if (r != null) {
        if (!r.isBT) {
            flag = false
        }
    }
    if (l != null) {
        min = minOf(min, l.min)
        max = maxOf(max, l.max)
    }
    if (r != null) {
        min = minOf(min, r.min)
        max = maxOf(max, r.max)
    }
    return Info(flag, max, min)
}

class Info(
    val isBT: Boolean,
    val max: Int,
    val min: Int
)



