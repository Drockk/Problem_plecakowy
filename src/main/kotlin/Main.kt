package org.example

import kotlin.random.Random

fun createAndMutateChild(obj: List<Boolean>): List<Boolean> {
    val position = Random.nextInt(0, 100)
    val result = obj.toMutableList()
    result[position] = !result[position]

    return result
}

fun sumItemsCapacity(obj: List<Boolean>, items: List<Int>): Int {
    var result = 0

    obj.forEachIndexed { index, element ->
        if (element) {
            result += items[index]
        }
    }

    return result
}

fun main() {
    val bagCapacity = 2500
    val itemCount = 100
    val itemCapacityMin = 10
    val itemCapacityMax = 90

    val items = List(itemCount) { Random.nextInt(itemCapacityMin, itemCapacityMax) }

    var parent = List(itemCount) { Random.nextBoolean() }
    var parentSum = sumItemsCapacity(parent, items)

    if (parentSum != bagCapacity) {
        for (it in (1..1000000)) {
            val child = createAndMutateChild(parent)
            val childSum = sumItemsCapacity(child, items)

            if (parentSum > bagCapacity && childSum > bagCapacity) {
                val parentDiff = parentSum - bagCapacity
                val childDiff = childSum - bagCapacity

                if (parentDiff > childDiff) {
                    parent = child
                    parentSum = childSum
                }
            } else if (parentSum > bagCapacity) {
                parent = child
                parentSum = childSum
            } else if (parentSum < childSum) {
                parent = child
                parentSum = childSum
            }
        }
    }

    println("Final result")
    println("Result: $parent")
    println("Capacity: $parentSum")
}