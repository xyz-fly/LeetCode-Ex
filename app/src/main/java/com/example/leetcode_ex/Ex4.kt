package com.example.leetcode_ex

import java.util.Arrays
import kotlin.math.max
import kotlin.math.min

/**
 * 滑动窗口 （连续）
 * 1、定滑窗口套路：
 * 入：加入窗口，更新统计量，一般是for循环的i，前面先加满K，后面从K开始遍历
 * 更新：更新答案，最大最小值
 * 出：start - k + 1 更新相关统计量，一般是 k-i
 * 模版
 *   for (start in 0..nums.size - 1) {
 *         sum += nums[start] //  相当于前进
 *         val end = start - k + 1
 *         if (end < 0) {
 *             continue
 *         }
 *         avg = maxOf(sum, avg)
 *         sum -= nums[end]  // 出去的要排除掉
 *     }
 *    for (i in 0..array.size - 1) {
 *         sum += array[i]
 *         if (i < k - 1) continue
 *         max = maxOf(sum, max)
 *         sum -= array[i - k + 1]
 *     }
 *
 * 2、不定长滑动窗口   子数组  单调性！
 * 解决：最长子数组，最短子数组，子数组个数
 * 维护一个队列，右指针入队，左指针出队
 *     var left = 0
 *     for (right in 0..nums.size - 1) {
 *         sum += nums[right]
 *         while (sum >= target) { // 业务条件
 *             // 处理结算
 *             // 结算前移
 *             sum -= nums[left]
 *             left++
 *         }
 *     }
 *
 * 3、前后双指针
 * 解决，数组有序，前后各有一个指针向中间移动
 */
fun main(args: Array<String>) {
//    lengthOfLongestSubstring("pwwkew")

    //println(maximumSubarraySum(intArrayOf(1, 1, 1, 7, 8, 9), 3))
//    maxScore(intArrayOf(1, 2, 3, 4, 5, 6, 1), 3)

    checkInclusion("ab", "eidbaooo")
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
 * 给你一个整数数组 nums 和一个整数 k 。请你从 nums 中满足下述条件的全部子数组中找出最大子数组和：
 * 子数组的长度是 k，且
 * 子数组中的所有元素 各不相同 。
 * 输入：nums = [1,5,4,2,9,9,9], k = 3
 * 输出：15
 * - [1,5,4] 满足全部条件，和为 10 。
 * - [5,4,2] 满足全部条件，和为 11 。
 * - [4,2,9] 满足全部条件，和为 15 。
 * - [2,9,9] 不满足全部条件，因为元素 9 出现重复。
 * - [9,9,9] 不满足全部条件，因为元素 9 出现重复
 *
 * 解题
 * 找一个Map记录出现次数
 * 更新答案
 */
fun maximumSubarraySum(nums: IntArray, k: Int): Long {
    if (nums.size == 0 || nums.size < k) return 0

    val check = mutableMapOf<Int, Int>()
    var sum = 0L
    var count = 0
    var max = Long.MIN_VALUE
    // 填充数据

    //  [1,5,4,2,9,9,9], k = 3
    for (i in 0..nums.size - 1) {
        val v = nums[i]
        if (check.get(v) == null) {
            check[v] = 1
        } else {
            check[v] = check[v]!! + 1
        }

        sum += v

        if (check[v]!! > 1) {
            count++
        }
        // 2    3
        if (i < k - 1) continue

        // 结算
        if (count == 0) {
            max = maxOf(max, sum)
        }

        val preV = nums[i - k + 1]
        if (check[preV]!! > 1) count--
        check[preV] = check[preV]!! - 1
        sum -= preV
    }
    return if (max == Long.MIN_VALUE) 0 else max
}


/**
 * 每次行动，你可以从行的开头或者末尾拿一张卡牌，最终你必须正好拿 k 张卡牌。
 * 你的点数就是你拿到手中的所有卡牌的点数之和。
 * 输入：cardPoints = [1,2,3,4,5,6,1], k = 3
 * 输出：12
 * 解释：第一次行动，不管拿哪张牌，你的点数总是 1 。但是，先拿最右边的卡牌将会最大化你的可获得点数。
 * 最优策略是拿右边的三张牌，最终点数为 1 + 6 + 5 = 12 。
 *
 * 解题，与中间数无关
 */
fun maxScore(cardPoints: IntArray, k: Int): Int {
    if (cardPoints.size == 0 || cardPoints.size < k) return 0
    val array = IntArray(k * 2)
    var index = 0
    for (i in cardPoints.size - k..cardPoints.size - 1) {
        array[index] = cardPoints[i]
        index++
    }
    for (i in 0..k - 1) {
        array[index] = cardPoints[i]
        index++
    }

    var max = Int.MIN_VALUE
    var sum = 0
    // [5,6,1,2,3]
    for (i in 0..array.size - 1) {
        sum += array[i]
        if (i < k - 1) continue
        max = maxOf(sum, max)
        sum -= array[i - k + 1]
    }
    return max
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
            sum -= nums[i - k * 2]
        }
    }
    return array
}

/**
 * 给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的 排列。如果是，返回 true ；否则，返回 false 。
 * 换句话说，s1 的排列之一是 s2 的 子串 。
 * 输入：s1 = "ab" s2 = "eidbaooo"
 * 输出：true
 * 解释：s2 包含 s1 的排列之一 ("ba").
 *
 * 解题
 *
 */
fun checkInclusion(s1: String, s2: String): Boolean {
    val char1 = s1.toCharArray()
    val char2 = s2.toCharArray()

    if (char1.size > char2.size) return false

    val check1 = IntArray(26)
    val check2 = IntArray(26)

    for (i in 0..char1.size - 1) {
        check1[char1[i] - 'a']++
    }

    for (right in 0..char2.size - 1) {

        val c = char2[right]
        check2[c - 'a']++
        if (right < char1.size - 1) continue // 补齐

        if (Arrays.equals(check1, check2)) {
            return true
        }
        check2[char2[right - char1.size + 1] - 'a']--
    }
    return false
}

/**
 * 拆炸弹
 * 如果 k > 0 ，将第 i 个数字用 接下来 k 个数字之和替换。
 * 如果 k < 0 ，将第 i 个数字用 之前 k 个数字之和替换。
 * 如果 k == 0 ，将第 i 个数字用 0 替换。
 * 输入：code = [5,7,1,4], k = 3
 * 输出：[12,10,16,13]
 *  [7+1+4, 1+4+5, 4+5+7, 5+7+1]
 * 输入：code = [2,4,9,3], k = -2
 * 输出：[12,5,6,13]
 * [3+9, 2+3, 4+2, 9+4]
 */
fun decrypt(code: IntArray, k: Int): IntArray {
    val result = IntArray(code.size)

    var r = if (k > 0) k + 1 else code.size
    val k = Math.abs(k)

    var sum = 0
    for (i in r - k..r - 1) {
        sum += code[i]
    }

    for (i in 0..code.size - 1) {
        result[i] = sum
        sum += code[r % code.size] - code[(r - k) % code.size]
        r++
    }
    return result
}

/**
 * 子串出现次数
 * 子串中不同字母的数目必须小于等于 maxLetters 。
 * 子串的长度必须大于等于 minSize 且小于等于 maxSize 。
 * 输入：s = "aababcaab", maxLetters = 2, minSize = 3, maxSize = 4
 * 输出：2
 * 解释：子串 "aab" 在原字符串中出现了 2 次。
 * 它满足所有的要求：2 个不同的字母，长度为 3 （在 minSize 和 maxSize 范围内）。
 *
 */
//fun maxFreq(s: String, maxLetters: Int, minSize: Int, maxSize: Int): Int {
//
//}

/**
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长 子串 的长度。
 * 输入: s = "abcabcbb"
 * 输出: 3
 */
fun lengthOfLongestSubstring(s: String): Int {
    var sum = 0
    var left = 0
    val check = IntArray(128)
    val chars = s.toCharArray()
    for (right in 0..chars.size - 1) {
        check[chars[right].code]++
        while (check[chars[right].code] > 1) {
            check[chars[left].code]--
            left++
        }
        sum = maxOf(sum, right - left + 1)
    }
    return sum
}

/**
 * 给定一个含有 n 个正整数的数组和一个正整数 target 。
 * 找出该数组中满足其总和大于等于 target 的长度最小的 子数组
 * 输入：target = 7, nums = [2,3,1,2,4,3]
 * 输出：2
 * 解释：子数组 [4,3] 是该条件下的长度最小的子数组
 */
fun minSubArrayLen1(target: Int, nums: IntArray): Int {
    if (nums.size == 0) return 0
    var left = 0
    var sum = 0
    var min = Int.MAX_VALUE
    for (right in 0..nums.size - 1) {
        sum += nums[right]
        while (sum >= target) {
            min = minOf(min, right - left + 1)
            sum -= nums[left]
            left++
        }
    }
    return if (min == Int.MAX_VALUE) 0 else min
}

/**
 * 输入：nums = [10,5,2,6], k = 100
 * 输出：8
 * 解释：8 个乘积小于 100 的子数组分别为：[10]、[5]、[2]、[6]、[10,5]、[5,2]、[2,6]、[5,2,6]。
 * 需要注意的是 [10,5,2] 并不是乘积小于 100 的子数组。
 */
fun numSubarrayProductLessThanK(nums: IntArray, k: Int): Int {
    if (k <= 1) {
        return 0
    }
    var sum = 1
    var left = 0
    var count = 0
    for (right in 0..nums.size - 1) {
        sum = sum * nums[right]
        while (sum >= k && left < right) {
            sum /= nums[left]
            left++
        }
        count += right - left + 1
    }
    return count
}

/**
 * 给你一个字符串 s ，请找出满足每个字符最多出现两次的最长子字符串，并返回该子字符串的 最大 长度
 * 输入： s = "bcbbbcba"
 * 输出： 4
 */
fun maximumLengthSubstring(s: String): Int {
    val chars = s.toCharArray()
    if (chars.size <= 1) return chars.size
    var left = 0
    val check = IntArray(128)
    var max = 0
    for (right in 0..chars.size - 1) {
        check[chars[right].code]++
        while (check[chars[right].code] > 2) {
            check[chars[left].code]--
            left++
        }
        max = maxOf(max, right - left + 1)
    }
    return max
}

/**
 * 给你一个二进制数组 nums ，你需要从中删掉一个元素。
 * 请你在删掉元素的结果数组中，返回最长的且只包含 1 的非空子数组的长度。
 * 输入：nums = [1,1,0,1]
 * 输出：3
 * 解释：删掉位置 2 的数后，[1,1,1] 包含 3 个 1 。
 *
 *  优化：直接跳到非1数位置遍历下一次
 */
fun longestSubarray(nums: IntArray): Int {
    if (nums.size == 0) return 0

    var left = 0
    var max = 0
    var count = 0
    for (right in 0..nums.size - 1) {
        if (nums[right] != 1) {
            count++
        }
        while (count > 1) {
            if (nums[left] != 1) {
                count--
            }
            left++
        }
        max = maxOf(max, (right - left + 1) - 1)
    }

    return max
}


/**
 * 给你一个字符串 s ，它只包含三种字符 a, b 和 c 。
 * 请你返回 a，b 和 c 都 至少 出现过一次的子字符串数目。
 * 输入：s = "abcabc"
 * 输出：10
 * 解释：包含 a，b 和 c 各至少一次的子字符串为 "abc", "abca", "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" 和 "abc" (相同字符串算多次)。
 * 输入：s = "aaacb"
 * 输出：3
 * 解释：包含 a，b 和 c 各至少一次的子字符串为 "aaacb", "aacb" 和 "acb" 。
 *
 * 解题：
 * 右边一直移动到满足条件的时候，左边就开始往前走，子数组梳理
 */
fun numberOfSubstrings(s: String): Int {
    val chars = s.toCharArray()
    var left = 0

    var sum = 0
    var a = 0
    var b = 0
    var c = 0
    for (right in 0..chars.size - 1) {
        val char = chars[right]
        if (char == 'a') {
            a++
        } else if (char == 'b') {
            b++
        } else if (char == 'c') {
            c++
        }
        while (a > 0 && b > 0 && c > 0) {
            if (chars[left] == 'a') {
                a--
            } else if (chars[left] == 'b') {
                b--
            } else if (chars[left] == 'c') {
                c--
            }
            left++
        }
        sum += left
    }
    return sum
}