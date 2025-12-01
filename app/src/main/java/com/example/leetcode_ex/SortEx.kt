package com.example.leetcode_ex


fun main(args: Array<String>) {
    val array = intArrayOf(8, 4, 9, 5, 6, 10, 3, 7, 2)
    bubble(array)
    array.forEach { print("$it,") }
    println()
    select(array)
    array.forEach { print("$it,") }
    println()
    insert(array)
    array.forEach { print("$it,") }
}

/**
 * 前面是有序
 */
fun insert(array: IntArray) {
    for (i in 1..array.size - 1) {
        for (j in i downTo 0) {
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

