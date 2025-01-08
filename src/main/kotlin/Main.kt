package org.example

import kotlin.random.Random
import kotlin.system.exitProcess

fun parseArguments(args: Array<String>): IntArray {
    check(args.isNotEmpty()) {
        "No arguments given!"
    }

    val argumentCount = 5
    check(args.count() == argumentCount) {
        "Wrong number of arguments"
    }

    return intArrayOf(
        args[0].toInt(),
        args[1].toInt(),
        args[2].toInt(),
        args[3].toInt(),
        args[4].toInt()
    )
}

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

fun main(args: Array<String>) {
    val parsedArguments = try {
        parseArguments(args)
    }
    catch (e: IllegalStateException) {
        println(e.message)
        exitProcess(-1)
    }

    val iterations = parsedArguments[0]
    val bagCapacity = parsedArguments[1]
    val itemCount = parsedArguments[2]
    val itemCapacityMin = parsedArguments[3]
    val itemCapacityMax = parsedArguments[4]

    println("Given Arguments")
    println("Iterations:            $iterations")
    println("Bag capacity:          $bagCapacity")
    println("Item count:            $itemCount")
    println("Item capacity minimum: $itemCapacityMin")
    println("Item capacity maximum: $itemCapacityMax")

    val items = List(itemCount) { Random.nextInt(itemCapacityMin, itemCapacityMax) }

    var parent = List(itemCount) { Random.nextBoolean() }
    var parentSum = sumItemsCapacity(parent, items)

    if (parentSum != bagCapacity) {
        for (it in (1..iterations)) {
            val child = createAndMutateChild(parent)
            val childSum = sumItemsCapacity(child, items)

            if (childSum == bagCapacity) {
                parent = child
                parentSum = childSum
                break
            }
            else if (parentSum > bagCapacity && childSum > bagCapacity) {
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

    println("")
    println("Final result")
    println("Packed items sizes")
    parent.zip(items).forEach {
            pair -> if (pair.first) {
        print("${pair.second} ")
    }
    }
    println("")
    println("Proposed capacity: $parentSum")
    println("Difference(Bag capacity - Proposed capacity): ${bagCapacity - parentSum}")
}