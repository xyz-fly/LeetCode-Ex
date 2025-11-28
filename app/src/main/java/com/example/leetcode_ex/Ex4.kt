package com.example.leetcode_ex

/**
 * 滑动窗口 （连续）
 * 1、定滑窗口套路：
 * 入：加入窗口，更新统计量，一般是for循环的i，前面先加满K，后面从K开始遍历
 * 更新：更新答案，最大最小值
 * 出：start - k + 1 更新相关统计量，一般是 k-i
 *
 *
 */
fun main(args: Array<String>) {
    getAverages(intArrayOf(7, 4, 3, 9, 1, 8, 5, 2, 6), 3)
}

/**
 * 给你字符串 s 和整数 k 。
 * 请返回字符串 s 中长度为 k 的单个子字符串中可能包含的最大元音字母数。
 * 英文中的 元音字母 为（a, e, i, o, u）
 * 输入：s = "abciiidef", k = 3
 * 输出：3
 * 解释：子字符串 "iii" 包含 3 个元音字母。
 * 输入：s = "aeiou", k = 2
 * 输出：2
 * 解释：任意长度为 2 的子字符串都包含 2 个元音字母。
 *
 * 解题
 */
fun maxVowels(s: String, k: Int): Int {
    val chars = s.toCharArray()
    var count = 0
    var max = 0
    for (start in 0..chars.size - 1) {
        if (chars[start] == 'a'
            || chars[start] == 'e'
            || chars[start] == 'i'
            || chars[start] == 'o'
            || chars[start] == 'u'
        ) {
            count++
        }

        val end = start - k + 1
        if (end < 0) { // 不足第一个窗口继续移动
            continue
        }
        if (count == k) { // 找到了就结束
            break
        }

        max = maxOf(max, count)
        if (chars[end] == 'a'
            || chars[end] == 'e'
            || chars[end] == 'i'
            || chars[end] == 'o'
            || chars[end] == 'u'
        ) {
            count--
        }
    }
    return max
}

fun findMaxAverage2(nums: IntArray, k: Int): Double {
    var sum = 0
    var avg = Int.MIN_VALUE
    for (start in 0..nums.size - 1) {
        sum += nums[start]
        val end = start - k + 1
        if (end < 0) {
            continue
        }
        avg = maxOf(sum, avg)
        sum -= nums[end]
    }
    return avg.toDouble() / k
}

/**
 * 给你一个整数数组 arr 和两个整数 k 和 threshold 。
 * 请你返回长度为 k 且平均值大于等于 threshold 的子数组数目。
 * 输入：arr = [2,2,2,2,5,5,5,8], k = 3, threshold = 4
 * 输出：3
 * 解释：子数组 [2,5,5],[5,5,5] 和 [5,5,8] 的平均值分别为 4，5 和 6 。其他长度为 3 的子数组的平均值都小于 4 （threshold 的值)。
 */
fun numOfSubarrays(arr: IntArray, k: Int, threshold: Int): Int {
    var sum = 0
    for (i in 0..k - 1) {
        sum += arr[i]
    }

    var count = 0
    if (sum / k >= threshold) {
        count++
    }
    println(sum.toString() + " " + (sum / k))
    sum -= arr[0]
    for (start in k..arr.size - 1) {
        sum += arr[start]
        println(sum.toString() + " " + (sum / k))

        if ((sum / k) >= threshold) {
            count++
        }
        sum -= arr[start - k + 1]

        println("sum -= arr[start - k] " + arr[start - k] + " " + sum)
    }
    return count
}

/**
 * 给你一个下标从 0 开始的数组 nums ，数组中有 n 个整数，另给你一个整数 k 。
 *
 * 输入：nums = [7,4,3,9,1,8,5,2,6], k = 3
 * 输出：[-1,-1,-1,5,4,4,-1,-1,-1]
 */
fun getAverages(nums: IntArray, k: Int): IntArray {
    val array = IntArray(nums.size)
    for (i in 0..array.size - 1) {
        array[i] = -1
    }

    var sum = 0
    for (i in 0..nums.size - 1) {
        sum += nums[i]

        if (i >= k * 2) {
            array[i - k] = sum / (k * 2 + 1)
            sum -= nums[i - k * 2 ]
        }
    }
    return array
}