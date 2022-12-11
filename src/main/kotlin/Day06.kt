fun main(args: Array<String>) {
    fun findUniqueMarker(input: String, numChars: Int): Int {
        val charCounts = mutableMapOf<Char, Int>().withDefault { 0 }
        for (i in input.indices) {
            val toAdd = input[i]
            charCounts[toAdd] = charCounts.getValue(toAdd) + 1
            if (i >= numChars - 1) {
                if (charCounts.keys.size == numChars) {
                    return i + 1
                } else {
                    val toRemove = input[i - (numChars - 1)]
                    if (charCounts[toRemove] == 1) {
                        charCounts.remove(toRemove)
                    } else {
                        charCounts[toRemove] = charCounts.getValue(toRemove) - 1
                    }
                }
            }
        }
        throw Exception("no match found")
    }

    fun part1(input: String) {
        println(findUniqueMarker(input, 4))
    }

    fun part2(input: String) {
        println(findUniqueMarker(input, 14))
    }

    val input = object {}.javaClass.getResource("Day06.txt").readText().trimEnd()

    part1(input)
    part2(input)
}
