fun main(args: Array<String>) {
    fun findCommonElement(strings: List<String>): Char {
        val sets = strings.map { it.toSet() }
        val intersection = sets.reduce { acc, set -> acc.intersect(set) }
        if (intersection.size != 1) {
            throw RuntimeException("expected exactly one match, found ${intersection.size}")
        }
        return intersection.elementAt(0)
    }

    fun findMispackedTypeInRow(row: String): Char? {
        if (row.isEmpty()) return null

        val left = row.substring(0, row.length / 2)
        val right = row.substring(row.length / 2, row.length)
        return findCommonElement(listOf(left, right))
    }

    fun scoreType(type: Char?): Int {
        return if (type == null) {
            0
        } else if (type.isLowerCase()) {
            type - 'a' + 1
        } else {
            type - 'A' + 27
        }
    }

    fun findBadge(rows: List<String>): Char {
        return findCommonElement(rows)
    }

    fun part1(input: String) {
        var sum = 0
        input.split("\n").forEach {
            val type = findMispackedTypeInRow(it)
            sum += scoreType(type)
        }
        println(sum)
    }

    fun part2(input: String) {
        val lines = input.split("\n")
        var i = 0
        var sum = 0
        while (i < lines.size && lines[i].isNotEmpty()) {
            val badge = findBadge(lines.subList(i, i + 3))
            sum += scoreType(badge)
            i += 3
        }
        println(sum)
    }

    val input = object {}.javaClass.getResource("Day03.txt").readText()
    part1(input)
    part2(input)
}
