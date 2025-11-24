package com.example.leetcode_ex

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.PriorityQueue

/**
 * 链表：
 * 前一个和后一个组成一对键值表，和HashMap配合 和优先队列配合排序
 */
fun main(args: Array<String>) {
}

/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

/**
 * 19. 删除链表的倒数第 N 个结点
 * 输入：head = [1,2,3,4,5], n = 2
 * 输出：[1,2,3,5]
 *
 * 解题
 * 1、重点，需要创建一个临时的头部节点
 */
fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
    if (head == null) return null
    if (head.next == null && n == 1) return null

    var temp = ListNode(0)
    temp.next = head

    // 1 2 3 4 5
    // c = 1  e = 1  s=n
    // c = 2  e = 2  s=n
    // c = 3  e = 3  s=1
    // c = 4  e = 4  s=2
    // c = 5  e = 5  s=3

    // c=1 e=2 s=1
    // c=2 e=3 s=2
    // c=3 e=4 s=3
    // c=4 e=5 s=4

    // 1 2
    //
    var end: ListNode? = temp
    var start: ListNode? = temp
    var count = 0
    while (end!!.next != null) {
        end = end.next
        count++
        if (count > n) {
            start = start!!.next!!

        }
    }

    start!!.next = start.next!!.next

    return temp.next
}

/**
 * 21. 合并两个有序链表
 * 将两个升序链表合并为一个新的 升序 链表并返回
 * 输入：l1 = [1,2,4], l2 = [1,3,4]
 * 输出：[1,1,2,3,4,4]
 *
 * 解题
 *
 */
fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
    if (list1 == null) return list2
    if (list2 == null) return list1
    var l1 = list1
    var l2 = list2
    var temp = ListNode(0)
    val result = temp

    while (l1 != null && l2 != null) {
        if (l1.`val` < l2.`val`) {
            temp.next = l1
            l1 = l1.next
        } else {
            temp.next = l2
            l2 = l2.next
        }
        temp = temp.next!!
    }

    if (l1 == null) {
        temp.next = l2
    } else {
        temp.next = l1
    }

    return result.next
}

/**
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 *
 *
 * 解题：
 * 1、借助优先级队列，取出来数组的头全部存入队列中
 * 2、因为放进去已经排好顺序，最顶就是最小，取出一个开始建立连接，再将next放进队列中，直到取完
 *
 * 1、二分法，两个两个合并，利用递归返回
 */
@RequiresApi(Build.VERSION_CODES.N)
fun mergeKLists(lists: Array<ListNode?>): ListNode? {
    if (lists == null) return null
    if (lists.size == 1) return lists.get(0)


    val head = ListNode(0)
    var temp = head
    val p = PriorityQueue<ListNode> { a, b -> a.`val` - b.`val` }

    for (i in 0..lists.size - 1) {
        if (lists[i] != null) {
            p.add(lists[i])
        }
    }


    while (p.size > 0) {
        val n = p.poll()
        if (n.next != null) {
            p.add(n.next)
        }
        temp.next = n
        temp = temp.next!!
    }

    return head.next
}

/**
 * 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点
 * 输入：head = [1,2,3,4]
 * 输出：[2,1,4,3]
 *
 * 解题
 * 临时指针指向尾
 * 头的指针指向尾的指针 尾的指针指向头
 * 临时变量的下一个开始指向头
 */
fun swapPairs(head: ListNode?): ListNode? {
    if (head == null) return null
    if (head.next == null) return head


    val p = ListNode(0)
    p.next = head


    // 1 2 3 4
    // s e
    // e s
    // 1
    // s
    var temp = p
    while (temp.next != null && temp.next!!.next != null) {
        var s1 = temp.next
        var s2 = temp.next!!.next

        temp.next = s2
        s1!!.next = s2!!.next
        s2!!.next = s1
        temp = s1
    }

    return p.next
}

/**
 * 给定一个已排序的链表的头 head ， 删除所有重复的元素，使每个元素只出现一次 。返回 已排序的链表 。
 *
 */
fun deleteDuplicates(head: ListNode?): ListNode? {
    if (head == null) return null

    var t = head
    while (t!!.next != null) {
        if (t.next!!.`val` == t!!.`val`) {
            t.next = t.next!!.next
        } else {
            t = t.next
        }
    }
    return head
}

/**
 * 输入：head = [1,2,3,3,4,4,5]
 * 输出：[1,2,5]
 */
fun deleteDuplicates2(head: ListNode?): ListNode? {
    if (head == null) return null
    if (head.next == null) return head
    val temp: ListNode = ListNode(0)
    temp.next = head
    var cur = temp

    // 1233445

    while (cur.next != null && cur.next!!.next != null) {
        if (cur.next!!.`val` == cur.next!!.next!!.`val`) {
            val a = cur.next!!.`val`
            while (cur.next != null && cur.next!!.`val` == a) {
                cur.next = cur.next!!.next
            }
        } else {
            cur = cur.next!!
        }
    }

    return temp.next
}

/**
 * 输入：head = [1,2,3,3,4,4,5]
 * 输出：[1,2,5]
 */
fun deleteDuplicates3(head: ListNode?): ListNode? {
    if (head == null) return null
    if (head.next == null) return head
    val temp: ListNode = ListNode(0)
    temp.next = head
    var cur = head
    var pre = temp
    var b = false
    // 1233445
    while (cur != null) {
        if (cur.next != null) {
            // 122
            // pc
            // p c
            if (cur.`val` == cur.next!!.`val`) {
                b = true
            } else {
                if (b) {
                    pre.next = cur.next
                } else {
                    pre.next = cur
                }
                pre = pre.next!!
                b = false
            }
        }
        cur = cur!!.next
    }
    pre.next = null
    return temp.next
}


/**
 * 输入：head = [4,5,1,9], node = 5
 * 输出：[4,1,9]
 * 输入：head = [4,5,1,9], node = 1
 * 输出：[4,5,9]
 */
fun deleteNode(node: ListNode?) {

}

/**
 * 输入: head = [1,2,3,4,5]
 * 输出: [1,3,5,2,4]
 * 输入: head = [2,1,3,5,6,4,7]
 * 输出: [2,3,6,7,1,5,4]
 *
 * 找两个分别记录两个开头，最后连在一起，最后要把最后一个被连的结尾置空
 */
fun oddEvenList(head: ListNode?): ListNode? {
    if (head == null || head.next == null || head.next!!.next == null) return head
    var aa: ListNode? = head
    var bb: ListNode? = head!!.next
    var ss: ListNode? = bb
    var cur = head.next!!.next

    var flag = true
    while (cur != null) {
        if (flag) {
            aa!!.next = cur
            aa = aa.next
        } else {
            bb!!.next = cur
            bb = bb!!.next
        }
        flag = !flag

        cur = cur.next
    }
    bb!!.next = null
    aa!!.next = ss
    return head
}

/**
 * 2181 合并之间的节点
 * 输入：head = [0,3,1,0,4,5,2,0]
 * 输出：[4,11]
 */
fun mergeNodes(head: ListNode?): ListNode? {
    if (head == null) return null

    val temp = ListNode(0)
    var result = temp

    var cur = head
    var sum = 0
    while (cur != null) {
        val x = cur.`val`
        if (x == 0) {
            if (sum != 0) {
                result.next = ListNode(sum)
                result = result.next!!
                sum = 0
            }
        } else {
            sum += x
        }
        cur = cur.next
    }
    return temp.next
}

/**
 * 插入排序，先查找，然后从头开始遍历找到位置 o(n2)
 * 输入: head = [4,2,1,3]
 * 输出: [1,2,3,4]
 *
 * 解题
 */
//fun insertionSortList(head: ListNode?): ListNode? {
//    if (head == null || head.next == null) return head
//}

/**
 * LRUCache lRUCache = new LRUCache(2);
 * lRUCache.put(1, 1); // 缓存是 {1=1}
 * lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
 * lRUCache.get(1);    // 返回 1
 * lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
 * lRUCache.get(2);    // 返回 -1 (未找到)
 * lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
 * lRUCache.get(1);    // 返回 -1 (未找到)
 * lRUCache.get(3);    // 返回 3
 * lRUCache.get(4);    // 返回 4
 *
 * 解析
 * 双端链表 配合 hashmap
 *
 */
class LRUCache(val capacity: Int) {
    val map = mutableMapOf<Int, Node>()

    class Node(val v: Int) {
        var n: Node? = null
    }

    var start: Node? = null
    var end: Node? = null
    var count = 0

    fun get(key: Int): Int {
        var n = map.get(key)

        if (n != null) {
            val temp = n.n




            return n.v
        } else {
            return -1
        }
    }

    fun put(key: Int, value: Int) {
        // 1 2 3 4
        // s     e
        val n = Node(value)
        if (count < capacity) {
            count++
            if (start == null) {
                start = n
            }
            if (end == null) {
                end = n
            } else {
                end!!.n = n
                end = end!!.n
            }
        } else {
            start = start!!.n
            end!!.n = n
            end = end!!.n
        }
        map.put(key, n)
    }

}