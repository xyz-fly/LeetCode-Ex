package com.example.leetcode_ex

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.graphics.rotationMatrix
import androidx.core.os.trace
import java.util.Arrays
import java.util.PriorityQueue

@RequiresApi(Build.VERSION_CODES.N)
fun main(args: Array<String>) {
//    twoSum(intArrayOf(2, 7, 11, 15), 9)
//    threeSum(intArrayOf(-1, 0, 1, 2, -1, -4))
//    fourSum(intArrayOf(2, 1, 0, -1), 2)
//    merge(intArrayOf(1), 1, intArrayOf(), 0)
//
//    longestCommonPrefix(arrayOf("flower", "flow", "flight"))

//    removeElement(intArrayOf(3, 2, 2, 3), 3)

//    search(intArrayOf(3, 1), 1)

//    findMaxAverage(intArrayOf(1, 12, -5, -6, 50, 3), 4)

//    subarraySum(intArrayOf(1, 1, 1), 2)
//    subarraySum(intArrayOf(1, 2, 3), 3)
    sortColors(intArrayOf(2, 0, 2, 1, 1, 0))
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
 * 14. 最长公共前缀
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 *
 * 解题
 * 1、默认第一个为目标
 * 2、取下一个，比较与目标谁更长，作为遍历的长度
 * 3、两个开始比较string，获得最后相等的位置
 * 4、截取字符串，作为新的目标
 */
fun longestCommonPrefix(strs: Array<String>): String {
    if (strs.size == 0) return ""

    var target = strs[0]
    for (i in 1..strs.size - 1) {
        var index = -1
        val str = strs[i]
        val length = minOf(str.length, target.length)
        for (j in 0..length - 1) {
            if (target.get(j) == str.get(j)) {
                index = j
            } else break
        }
        if (index == -1) return ""
        target = target.substring(0, index + 1) // +1
    }
    return target
}

/**
 * 27. 移除元素
 * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素。
 * 元素的顺序可能发生改变。然后返回 nums 中与 val 不同的元素的数量。
 *
 * 输入：nums = [0,1,2,2,3,0,4,2], val = 2
 * 输出：5, nums = [0,1,4,0,3,_,_,_]
 *
 * 解题
 * 1、顺序可变，意味着可交换
 * 2、慢指针的含义是：该位置不能是目标，快指针走的时候，遇到不是目标就将结果赋值给慢指针的位置
 * 3、慢指针记录不与其相等的位置，快指针就是遍历的i
 * 4、移动了多少步，就是不相同的数量
 */
fun removeElement(nums: IntArray, `val`: Int): Int {
    if (nums.size == 0) return 0
    // [2,2,3,3,4] 2
    //  s   e
    var left = 0
    for (i in 0..nums.size - 1) {
        if (nums[i] != `val`) {
            nums[left] = nums[i]
            left++
        }
    }
    return left
}

/**
 * 33. 搜索旋转排序数组
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。时间复杂度为 O(log n)
 * 输入：nums = [4,5,6,7,0,1,2], target = 0
 * 输出：4
 *
 * 思路：
 * 1、数组分成有序的两段，前一段每个数组一定大于后一段每个数组
 * 2、如果其中一个数字与最后一个比较，如果大于最后一个数字，一定落在第一段内，否则落在第二段内
 *
 * 解题
 * 二分查找法 要求时间复杂度logN，必然二分查找法
 * 如果在一边是有序，目标在有序就直接在有序中查找，找不，设置边界，是一种类似排除法
 * 1、取中间位置，如果相等，直接结束
 * 2、如果当前值比左边大，看目标值是否落在这个区间里，如果在，缩小升序空间
 */
fun search(nums: IntArray, target: Int): Int {
    var left = 0
    var right = nums.size - 1
    while (left <= right) {
        val mid = left + (right - left) / 2
        val v = nums[mid]
        if (v == target) {
            return mid
        }
        // 4 5 6 7 8 9 1 2 3
        // 6 7 8 0 1 2 3 4 5
        if (nums[mid] >= nums[left]) {
            if (target >= nums[left] && target < nums[mid]) {
                right = mid - 1
            } else {
                left = mid + 1
            }
        } else {
            if (target > nums[mid] && target <= nums[right]) {
                left = mid + 1
            } else {
                right = mid - 1
            }
        }
    }
    return -1
}

/**
 * 31. 下一个排列
 */
fun nextPermutation(nums: IntArray): Unit {

}

/**
 * 39. 组合总和
 * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，
 * 找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合
 * 输入：candidates = [2,3,6,7], target = 7
 * 输出：[[2,2,3],[7]]
 *
 * 解释
 *
 */


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
 * 3、如果 当前值 的前一个存在（nums[i]-1) 则不是头 否则是头
 * 4、优化点：如果找了一条长度大于 set 的一半 那就不可能还有另一条大于一半的 直接返回
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

/////////////////////////////////////////////
// 双指针
/////////////////////////////////////////////

/**
 * 75. 颜色分类
 * 输入：nums = [2,0,2,1,1,0]
 * 输出：[0,0,1,1,2,2]
 *
 * 解题
 * 前后两个指针，当后面指针交换后，游标指针不要动，还得再次检查一下交换过来的是否正确
 */
fun sortColors(nums: IntArray): Unit {
    var p0 = 0
    var p2 = nums.size - 1
    var i = 0
    while (i <= p2) {
        if (nums[i] == 0) {
            swap(nums, i, p0)
            p0++
            i++
        } else if (nums[i] == 2) {
            swap(nums, i, p2)
            p2--
        } else {
            i++
        }
    }
}

fun swap(nums: IntArray, i: Int, j: Int) {
    val temp = nums[i]
    nums[i] = nums[j]
    nums[j] = temp
}

/**
 * 845. 数组中的最长山脉
 * 给出一个整数数组 arr，返回最长山脉子数组的长度。如果不存在山脉子数组，返回 0
 * 输入：arr = [2,1,4,7,3,2,5]
 * 输出：5
 * 解释：最长的山脉子数组是 [1,4,7,3,2]，长度为 5。
 *
 * 解题
 * 局部最小：[0]<[1] 或者 [i-1]>[i]<[i+1] 或者 [n-2]>[n-1]
 * 用二分找 中间 比 左变小 在这里找
 * 找到山峰后，向两边找到谷底
 */
fun longestMountain(arr: IntArray): Int {
    var max = 0
    for (i in 1..arr.size - 2) {
        // 山峰
        if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
            var r = i
            var l = i
            while (l > 0 && arr[l] > arr[l - 1]) l--
            while (r < arr.size - 1 && arr[r] > arr[r + 1]) r++
            max = maxOf(max, r - l + 1)
        }
    }
    return max
}


/////////////////////////////////////////////
// 滑动窗口
/////////////////////////////////////////////
/**
 * 53. 最大子数组和
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 *
 */
//fun maxSubArray(nums: IntArray): Int {
//
//}


/**
 * 643. 子数组最大平均数 I
 * 给你一个由 n 个元素组成的整数数组 nums 和一个整数 k
 * 请你找出平均数最大且 长度为 k 的连续子数组，并输出该最大平均数。
 * 输入：nums = [1,12,-5,-6,50,3], k = 4
 * 输出：12.75
 * 解释：最大平均数 (12-5-6+50)/4 = 51/4 = 12.75
 */
fun findMaxAverage(nums: IntArray, k: Int): Double {
    var sum = 0
    for (i in 0..k - 1) {
        sum += nums[i]
    }
    var max = sum
    for (i in k..nums.size - 1) {
        sum += nums[i] - nums[i - k]
        max = maxOf(sum, max)
    }
    return max.toDouble() / k
}

/////////////////////////////////////////////
// 前缀和   x-y的和 Array[y] - Array[x-1]
/////////////////////////////////////////////
/**
 * 560. 和为 K 的子数组
 * 给定一个整数数组和一个整数 k ，请找到该数组中和为 k 的连续子数组的个数。
 * 输入:nums = [1,1,1], k = 2
 * 输出: 2
 * 解释: 此题 [1,1] 与 [1,1] 为两种不同的情况
 */
fun subarraySum(nums: IntArray, k: Int): Int {
    val map = hashMapOf<Int, Int>()
    map[0] = 1

    var sum = 0
    var count = 0

    for (i in nums) {
        sum += i
        val target = sum - k
        count += map.get(target) ?: 0
        map.put(sum, (map.get(sum) ?: 0) + 1)
    }
    return count
}


/////////////////////////////////////////////
// 前缀和   x-y的和 Array[y] - Array[x-1]
/////////////////////////////////////////////
/**
 * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[1,2,3,6,9,8,7,4,5]
 *
 * 解题
 * 按顺序逐层递减，但要注意，最后两个增加边界判断 top <= bottom 和  left <= right
 */
fun spiralOrder(matrix: Array<IntArray>): List<Int> {
    // 1,2,3
    // 4,5,6
    // 7,8,9
    var top = 0
    var left = 0
    var right = matrix[0].size - 1
    var bottom = matrix.size - 1
    val list = mutableListOf<Int>()

    while (top <= bottom && left <= right) {
        for (i in left..right) {
            list += matrix[top][i]
        }

        top++

        for (i in top..bottom) {
            list += matrix[i][right]
        }
        right--

        if (top <= bottom) {
            for (i in right downTo left) {
                list += matrix[bottom][i]
            }
        }
        bottom--
        if (left <= right) {
            for (i in bottom downTo top) {
                list += matrix[i][left]
            }
        }
        left++
    }
    return list
}

/**
 * 209. 长度最小的子数组
 * 找出该数组中满足其总和大于等于 target 的长度最小的 子数组
 * 输入：target = 7, nums = [2,3,1,2,4,3]
 * 输出：2
 * 解释：子数组 [4,3] 是该条件下的长度最小的子数组。
 *
 * 解题
 * 1、快慢指针，尾指针向前移动，大于了，结算长度，让开始指针移动
 * 2、和是累加的，当尾指针向前，就把数加进去，尾向前就把数据减出去，总是维持窗口内的一个总大小
 */
fun minSubArrayLen(target: Int, nums: IntArray): Int {
    if (nums.size == 0) return 0
    if (nums.size == 1 && nums[0] >= target) 1 else 0

    var start = 0
    var end = 0
    var count = Int.MAX_VALUE

    var sum = 0
    while (end < nums.size) {
        // 2,3,1,2,4,3
        sum += nums[end]
        while (sum >= target) {
            count = minOf(count, end - start + 1)
            sum -= nums[start]
            start++
        }
        end++
    }
    return if (count == Int.MAX_VALUE) 0 else count
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

    for (i in 0..m + n - 1) {
        nums1[i] = array[i]
    }
}

/**
 * 反向双指针，从后面开始，节省一个数组
 */
fun merge2(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {

}

/**
 * 26. 删除有序数组中的重复项
 * 非严格递增排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，
 * 返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。
 * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
 * 输出：5, nums = [0,1,2,3,4,_,_,_,_,_]
 *
 * 解题
 * 快慢指针，需要快指针遇到不相同的，就给慢指针的位置赋值，慢指针前进
 */
fun removeDuplicates(nums: IntArray): Int {
    var index = 1
    for (i in 1..<nums.size) {
        if (nums[i] != nums[i - 1]) nums[index++] = nums[i]
    }
    return index
}

/**
 * 136. 只出现一次的数字
 * 给你一个 非空 整数数组 nums ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * 输入：nums = [4,1,2,1,2]
 * 输出：4
 *
 *解题
 */
//fun singleNumber(nums: IntArray): Int {
//
//}


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
