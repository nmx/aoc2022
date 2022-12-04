import java.util.PriorityQueue

fun main(args: Array<String>) {
    val input = object {}.javaClass.getResource("Day01.txt").readText()
    part1(input)
    part2(input)
}

fun part1(input: String) {
    var max = 0
    var sum = 0

    input.split("\n").forEach {
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

fun part2(input: String) {
    var sum = 0
    val heap: PriorityQueue<Int> = PriorityQueue<Int>(Comparator.reverseOrder())


    input.split("\n").forEach {
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
