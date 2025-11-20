package com.example.leetcode_ex

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Arrays
import java.util.PriorityQueue

@RequiresApi(Build.VERSION_CODES.N)
fun main(args: Array<String>) {
//    twoSum(intArrayOf(2, 7, 11, 15), 9)
//    threeSum(intArrayOf(-1, 0, 1, 2, -1, -4))
//    fourSum(intArrayOf(2, 1, 0, -1), 2)

    merge(intArrayOf(1), 1, intArrayOf(), 0)
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
 * 优化：如果排序后，连续三个相加都大于0，则肯定找不到了，如果和最后两个相加小于0，那也说明找不到了，继续
 */
fun threeSum(nums: IntArray): List<List<Int>> {
    Arrays.sort(nums)
    val list = arrayListOf<List<Int>>()
    for (index in 0..nums.size - 3) {
        // 放弃第一个 [0, 0, 1, 2, 3]
        if (index > 0 && nums[index - 1] == nums[index]) continue
        // [-1,0,2,3,4]
        if (nums[index].toLong() + nums[index + 1] + nums[index + 2] > 0) break // 优化
        // [-10, -1, 0, 1, 3]
        if (nums[index].toLong() + nums[nums.size - 1] + nums[nums.size - 2] < 0) continue // 优化

        var left = index + 1
        var right = nums.size - 1
        val target = nums[index]
        //  -3, -1, 0, 2, 3
        while (left < right) { // 不能相遇
            val sum = target + nums[left] + nums[right]
            if (sum == 0) {
                list.add(listOf(target, nums[left], nums[right]))

                while (left < right && nums[left] == nums[left + 1]) left++
                while (left < right && nums[right] == nums[right - 1]) right--

                // 如果上面一步没走，这里要自己向前走
                left++
                right--
            } else if (sum < 0) {
                left++
            } else {
                right--
            }
        }
    }
    return list
}

/**
 * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target
 * 请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]] （若两个四元组元素一一对应，则认为两个四元组重复）：
 * 输入：nums = [1,0,-1,0,-2,2], target = 0
 * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 *
 * 解题
 * 1、先排序数组，从小到大
 * 2、双循环，内循环配双指针，内部还是找3个
 *
 * 优化点：如果和后面三个相加已经大于目标，直接放弃，如果和最后三个相加小于目标，可以继续下一个，否则进入循环
 */
fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
    if (nums.size < 4) return listOf()
    Arrays.sort(nums)
    val list = arrayListOf<List<Int>>()
    //  -3, -1, 0, 2, 3
    for (i in 0..nums.size - 4) {
        if (i > 0 && nums[i] == nums[i - 1]) continue
        if (nums[i].toLong() + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) break
        if (nums[i].toLong() + nums[nums.size - 1] + nums[nums.size - 2] + nums[nums.size - 3] < target) continue

        val sum = target - nums[i]

        for (j in i + 1..nums.size - 3) {
            if (j > i + 1 && nums[j] == nums[j - 1]) continue
            if (nums[i].toLong() + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) break
            if (nums[i].toLong() + nums[nums.size - 1] + nums[nums.size - 2] + nums[nums.size - 3] < target) continue

            var left = j + 1
            var right = nums.size - 1

            while (left < right) {
                val result = nums[j] + nums[left] + nums[right]
                if (sum == result) {
                    list.add(listOf(nums[i], nums[j], nums[left], nums[right]))

                    while (left < right && nums[left] == nums[left + 1]) left++
                    while (left < right && nums[right] == nums[right - 1]) right--

                    left++
                    right--
                } else if (result < sum) {
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
 * 128. 最长连续序列
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度
 * 输入：nums = [100,4,200,1,3,2]
 * 输出：4
 *
 * 解题
 * 1、把数字加入set中，过滤掉重复的
 * 2、遍历set，在set中查询下一个是否存在
 */
fun longestConsecutive(nums: IntArray): Int {
    val set = mutableSetOf<Int>()
    nums.forEach {
        set += it
    }

    var count = 0
    set.forEach {
        // 100,4,200,1,3,2
        if (set.contains(it - 1)) {

        } else {
            var next = it + 1

            while (set.contains(next)) {
                next++
            }

            val newC = next - it
            count = maxOf(newC, count)
        }

        if (count * 2 >= set.size) return count
    }
    return count
}

/**
 * 283. 移动零
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序
 * 输入: nums = [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 *
 * 解题，双指针
 * 遇到非零，两个位置交换一下，0的指针向前，它挪一下前面只能是0
 */
fun moveZeroes(nums: IntArray): Unit {
    if (nums.size <= 1) return
    var zeroIndex = 0
    for (i in 0..nums.size - 1) {
        if (nums[i] != 0) {
            val temp = nums[i]
            nums[i] = nums[zeroIndex]
            nums[zeroIndex] = temp
            zeroIndex++
        }
    }
}

/**
 * 88. 合并两个有序数组
 * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目
 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 * 合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n
 *
 *
 * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * 输出：[1,2,2,3,5,6]
 * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
 * 合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
 *
 * 解题
 * 1、建立双指针，从后面排可以省略一个数组，从前面排，需要单独建立一个数组
 * 2、循环的条件是：两个指针都没有遍历到末尾
 * 3、如果有一遍填完了，就要将剩余的全部加入到里面
 * 4、注意，遍历的时候，两个指针都不要越过数组最大长度
 */
fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
    var p1 = 0
    var p2 = 0
    var arrP = 0
    val array = IntArray(m + n)
    while (p1 < m || p2 < n) {
        if (p1 == m) {
            array[arrP] = nums2[p2]
            p2++
        } else if (p2 == n) {
            array[arrP] = nums1[p1]
            p1++
        } else if (nums1[p1] <= nums2[p2]) {
            array[arrP] = nums1[p1]
            p1++
        } else {
            array[arrP] = nums2[p2]
            p2++
        }
        arrP++
    }

    for(i in 0..m+n -1 ){
        nums1[i] = array[i]
    }
}

/**
 * 反向双指针，从后面开始，节省一个数组
 */
fun merge2(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {

}



///**
// * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
// * 输入: [3,2,1,5,6,4], k = 2
// * 输出: 5
// * 输入: [3,2,3,1,2,4,5,5,6], k = 4
// * 输出: 4
// */
//fun findKthLargest(nums: IntArray, k: Int): Int {
//
//}

@RequiresApi(Build.VERSION_CODES.N)
fun PriorityQueueEX() {
    val heap = PriorityQueue(Comparator { a: Int, b: Int -> a - b })
    val h = PriorityQueue<Int>()

    heap.add(3)
    heap.add(4)
    heap.add(2)
    heap.add(0)

    heap.forEach {
        println(it)
    }

    println(heap.poll())
    println(heap.poll())
}