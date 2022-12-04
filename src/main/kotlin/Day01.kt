import java.io.File
import java.util.PriorityQueue

fun main(args: Array<String>) {
    val filename = "input/Day01.txt"
    part1(filename)
    part2(filename)
}

fun part1(filename: String) {
    var max = 0
    var sum = 0

    File(filename).forEachLine {
        if (it == "") {
            if (sum > max) {
                max = sum
            }
            sum = 0
        } else {
            sum += it.toInt()
        }
    }
    if (sum > max) {
        max = sum
    }
    println(max)
}

fun part2(filename: String) {
    var sum = 0
    val heap: PriorityQueue<Int> = PriorityQueue<Int>(Comparator.reverseOrder())


    File(filename).forEachLine {
        if (it == "") {
            heap.add(sum)
            sum = 0
        } else {
            sum += it.toInt()
        }
    }
    heap.add(sum)

    var result = 0
    for (i in 1..3) {
        result += heap.remove()
    }

    println(result)
}
