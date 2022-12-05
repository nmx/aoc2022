fun main(args: Array<String>) {
    fun toRange(str: String): IntRange {
        val (lo, hi) = str.split("-")
        return IntRange(lo.toInt(), hi.toInt())
    }

    fun rangesFullyContained(a: IntRange, b: IntRange): Boolean {
        return (a.first <= b.first && a.last >= b.last) ||
                (b.first <= a.first && b.last >= a.last)
    }

    fun rangesOverlap(a: IntRange, b: IntRange): Boolean {
        return (a.first <= b.first && a.last >= b.first) ||
                (b.first <= a.first && b.last >= a.first)
    }

    fun rowMeetsCriteria(row: String, func: (a: IntRange, b:IntRange) -> Boolean): Boolean {
        if (row.isEmpty()) return false

        val (left, right) = row.split(",")
        val leftRange = toRange(left)
        val rightRange = toRange(right)

        return func(leftRange, rightRange)
    }

    fun partN(input: String, func: (a: IntRange, b:IntRange) -> Boolean) {
        var sum = 0
        input.split("\n").forEach {
            if (rowMeetsCriteria(it, func))
                sum++
        }
        println(sum)
    }

    fun part1(input: String) {
        partN(input, ::rangesFullyContained)
    }

    fun part2(input: String) {
        partN(input, ::rangesOverlap)
    }

    val input = object {}.javaClass.getResource("Day04.txt").readText()
    part1(input)
    part2(input)
}
