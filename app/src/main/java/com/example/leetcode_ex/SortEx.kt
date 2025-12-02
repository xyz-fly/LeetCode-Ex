package com.example.leetcode_ex


fun main(args: Array<String>) {
    printArray { bubble(it) }
    printArray { select(it) }
    printArray { insert(it) }
    printArray { mergeSort(it) }
}

fun printArray(func: (IntArray) -> Unit) {
    val array = intArrayOf(8, 4, 9, 5, 6, 10, 3, 7, 2)
    func(array)
    array.forEach { print("$it,") }
    println()
}

fun mergeSort(array: IntArray) {
    mergeSplit(array, 0, array.size - 1)
}

fun mergeSplit(array: IntArray, start: Int, end: Int) {
    if (start == end) {
        return
    }
    val middle = start + (end - start) / 2
    mergeSplit(array, start, middle)
    mergeSplit(array, middle + 1, end)
    mergeSort(array, start, middle + 1, end)
}

fun mergeSort(array: IntArray, start1: Int, start2: Int, end: Int) {
    val len = start2 - start1
    val temp = array.copyOfRange(start1, start1 + len)

    var p1 = 0
    var p2 = start2
    for (i in start1..end) {
        if (temp[p1] < array[p2]) {
            array[i] = temp[p1]
            p1++
            if (p1 == len) {
                break
            }
        } else {
            array[i] = array[p2]
            p2++
            if (p2 > end) {
                var i2 = i
                while (p1 < len) {
                    i2++
                    array[i2] = temp[p1]
                    p1++
                }
                break
            }
        }
    }
}


/**
 * 前面是有序
 */
fun insert(array: IntArray) {
    for (i in 1..array.size - 1) {
        for (j in i downTo 1) {
            if (array[j] < array[j - 1]) {
                arraySwap(array, j, j - 1)
            } else {
                break
            }
        }
    }
}

/**
 * 找最小值
 */
fun select(array: IntArray) {
    for (i in 0..array.size - 2) {
        var min = i
        for (j in i + 1..array.size - 1) {
            if (array[j] < array[min]) {
                min = j
            }
        }
        if (i != min) {
            arraySwap(array, min, i)
        }
    }
}

fun bubble(array: IntArray) {
    for (i in array.size - 1 downTo 1) {
        var flag = true
        for (j in 0..i - 1) {
            if (array[j] > array[j + 1]) {
                arraySwap(array, j, j + 1)
                flag = false
            }
        }
        if (flag) {
            break
        }
    }
}

fun arraySwap(array: IntArray, a: Int, b: Int) {
    val temp = array[a]
    array[a] = array[b]
    array[b] = temp
}

