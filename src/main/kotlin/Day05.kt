fun main(args: Array<String>) {
    fun parseStacks(input: List<String>): Pair<List<MutableList<Char>>, List<String>> {
        val stackLabels = input.find { s -> s.startsWith(" 1") }!!
        val numStacks = stackLabels.split(" ").last { s -> !s.isEmpty() }.toInt()

        val stacks = mutableListOf<MutableList<Char>>()
        for (i in 1..numStacks) {
            stacks.add(mutableListOf())
        }

        var i = 0
        while (!input[i].startsWith(" 1")) {
            val line = input[i]
            var j = 0
            while (j < numStacks) {
                val c = line[j * 4 + 1]
                if (c != ' ') {
                    stacks[j].add(c)
                }
                j += 1
            }
            i++
        }
        return Pair(stacks, input.subList(i + 2, input.size))
    }

    fun executeMoves(stacks: List<MutableList<Char>>, moves: List<String>, reverseOrder: Boolean) {
        moves.filter{ it.isNotEmpty() }.forEach {
            val (num, src, dst) = it.split(" ").mapNotNull { s -> s.toIntOrNull() }
            if (reverseOrder) {
                for (i in num - 1 downTo 0) {
                    stacks[dst - 1].add(0, stacks[src - 1].removeAt(i))
                }
            } else {
                for (i in 1..num) {
                    stacks[dst - 1].add(0, stacks[src - 1].removeAt(0))
                }
            }
        }
    }

    fun resultString(stacks: List<List<Char>>): String {
        return stacks.map { it.first() }.joinToString("")
    }

    fun part1(input: String) {
        val (stacks, moves) = parseStacks(input.split("\n"))
        executeMoves(stacks, moves, false)
        println(resultString(stacks))
    }

    fun part2(input: String) {
        val (stacks, moves) = parseStacks(input.split("\n"))
        executeMoves(stacks, moves, true)
        println(resultString(stacks))
    }

    val input = object {}.javaClass.getResource("Day05.txt").readText()

    part1(input)
    part2(input)
}
