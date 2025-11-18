package com.example.leetcode_ex

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Arrays
import java.util.PriorityQueue

fun main(args: Array<String>) {
//    twoSum(intArrayOf(2, 7, 11, 15), 9)
    threeSum(intArrayOf(-1, 0, 1, 2, -1, -4))
}

/**
 * 用map记录一下当前遍历，key为数值，value为下标
 * 下一次遍历直接从map中有没有对应的数值
 *
 *  twoSum(intArrayOf(2, 7, 11, 15), 9)
 */
fun twoSum(nums: IntArray, target: Int): IntArray {
    val map = mutableMapOf<Int, Int>()
    nums.forEachIndexed { index, value ->
        val s = target - value
        if (map.containsKey(s)) {  // 找之前的数值
            return intArrayOf(map[s]!!, index)
        }
        map[value] = index // 记录下数值和下标
    }
    return intArrayOf()
}

/**
 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，
 * 同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。
 *
 * 解题
 * 1、先排序数组，从小到大
 * 2、双循环，内部第一次循环，先跳过重复的元素，然后建立双指针，
 * 3、从当前元素下一个，和最后一个，从前后向中间遍历，不能相遇（left<right）如果小了，大边后退，大了，小边前进
 * 4、找到以后，记录下来，然后继续前进，找下一个，并过滤掉重复元素
 */
fun threeSum(nums: IntArray): List<List<Int>> {
    Arrays.sort(nums)
    val list = arrayListOf<List<Int>>()
    nums.forEachIndexed { index, target ->
        if (index > 0 && nums[index - 1] == target) {

        } else {
            var left = index + 1
            var right = nums.size - 1

//  -3, -1, 0, 2, 3
            while (left < right) { // 不能相遇
                val sum = target + nums[left] + nums[right]
                if (sum == 0) {
                    list.add(listOf(target, nums[left], nums[right]))

                    while (left < right && nums[left] == nums[left + 1]) left++
                    while (left < right && nums[right] == nums[right - 1]) right--
                    left++
                    right--
                } else if (sum < 0) {
                    left++
                } else {
                    right--
                }
            }
        }
    }
    return list
}

/**
 * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
 * 输入：nums = [1,1,1,2,2,3], k = 2
 * 输出：[1,2]
 *
 * 解题
 * 1、先统计每个数字的总数
 * 2、建立一个堆，如果总数小于堆总数，直接加入
 * 3、如果等于堆总数了，比较顶部与当前的大小，谁大谁加入
 *
 * 1、建立一个数组，利用数组下标来记录出现次数，保存的数据是list，里面存储出现的这个次数的数值
 * 2、从最后一位开始遍历，因为这个次数最多，如果有数值，遍历list，并统计最后是否够K了
 */
@RequiresApi(Build.VERSION_CODES.N)
fun topKFrequent(nums: IntArray, k: Int): IntArray {
    val map = mutableMapOf<Int, Int>()
    nums.forEach {
        map.put(it, (map[it] ?: 0) + 1)
    }

    val heap = PriorityQueue(Comparator { a: Int, b: Int -> map[a]!! - map[b]!! })
    map.keys.forEach {
        heap.add(it)
        if (heap.size > k) heap.poll()
    }
    val a = IntArray(k)
    for (i in k - 1 downTo 0) {
        a[i] = heap.poll()
    }
    return a
}

/**
 * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
 * 输入: [3,2,1,5,6,4], k = 2
 * 输出: 5
 * 输入: [3,2,3,1,2,4,5,5,6], k = 4
 * 输出: 4
 */
fun findKthLargest(nums: IntArray, k: Int): Int {

}

