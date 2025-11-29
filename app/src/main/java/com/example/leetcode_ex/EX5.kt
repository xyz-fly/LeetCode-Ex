package com.example.leetcode_ex

import android.os.Build
import androidx.annotation.RequiresApi

/**
 * 前缀和
 * [1,2,3,4,5] 可以表达为 [1,2] + [3,4,5]
 * 模版：
 * 构建 sum 长度 nums.size + 1
 * 赋值 sum[i+1] = sum[i] +nums[i]
 * 范围 sum[right+1] - sum[left]
 */

fun main(args: Array<String>) {
}


val nums = intArrayOf()
val sumMap = IntArray(nums.size + 1)

fun init() {
    for (i in 0..nums.size - 1) {
        sumMap[i + 1] = sumMap[i] + nums[i]
    }
}

fun sumRange(left: Int, right: Int): Int {
    return sumMap[right + 1] - sumMap[left]
}

/**
 * 给你一个整数数组 arr 。请你返回和为 奇数 的子数组数目。
 * 输入：arr = [1,3,5]
 * 输出：4
 * 解释：所有的子数组为 [[1],[1,3],[1,3,5],[3],[3,5],[5]] 。
 * 所有子数组的和为 [1,4,9,3,8,5].
 * 奇数和包括 [1,9,3,5] ，所以答案为 4
 *
 *
 */
fun numOfSubarrays(arr: IntArray): Int {
    val cnt = IntArray(2)
    var ans: Long = 0
    var s = 0
    for (x in arr) {
        cnt[s and 1]++
        s += x
        ans += cnt[(s and 1) xor 1].toLong()
    }

    return (ans % 1_000_000_007).toInt()
}

/**
 * 给定一个整数数组 nums 和一个整数 k ，返回其中元素之和可被 k 整除的非空 子数组 的数目。
 * 输入：nums = [4,5,0,-2,-3,1], k = 5
 * 输出：7
 * 解释：
 * 有 7 个子数组满足其元素之和可被 k = 5 整除：
 * [4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
 */
@RequiresApi(Build.VERSION_CODES.N)
fun subarraysDivByK(nums: IntArray, k: Int): Int {
    val map = mutableMapOf<Int, Int>()
    var s = 0
    var sum = 0

    for (i in 0..nums.size - 1) {
        map.merge(s, 1, Integer::sum)
        s = (s + nums[i] % k + k) % k
        sum += map.get(s) ?: 0
    }
    return sum
}