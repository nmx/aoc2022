fun main(args: Array<String>) {
    class Shape(
        val draw: Char,
        val defeats: Char,
        val score: Int
    )

    val shapes = buildMap<Char, Shape> {
        put('X', Shape('A', 'C', 1)) // Rock smashes Scissors
        put('Y', Shape('B', 'A', 2)) // Paper covers Rock
        put('Z', Shape('C', 'B', 3)) // Scissors cuts Paper
    }

    class OpponentShape(win: Char, lose: Char, draw: Char) {
        val win = shapes[win]!!
        val lose = shapes[lose]!!
        val draw = shapes[draw]!!
    }

    val opponentShapes = buildMap<Char, OpponentShape> {
        put('A', OpponentShape('Y', 'Z', 'X'))
        put('B', OpponentShape('Z', 'X', 'Y'))
        put('C', OpponentShape('X', 'Y', 'Z'))
    }

    fun scoreRow(row: String, secondColumIsWLD: Boolean): Int {
        if (row.isEmpty())
            return 0

        val opponent = row[0]
        val shape = if (secondColumIsWLD) {
            val opponentShape = opponentShapes[opponent]!!
            when (row[2]) {
                'X' -> opponentShape.lose
                'Y' -> opponentShape.draw
                'Z' -> opponentShape.win
                else -> throw RuntimeException("unhandled input")
            }
        } else {
            shapes[row[2]]!!
        }

        return shape.score + when (opponent) {
            shape.defeats -> 6
            shape.draw -> 3
            else -> 0
        }
    }

    fun partN(input: String, secondColumIsWLD: Boolean) {
        var score = 0
        input.split("\n").forEach {
            score += scoreRow(it, secondColumIsWLD)
        }
        println(score)
    }

    fun part1(input: String) {
        partN(input, false)
    }

    fun part2(input: String) {
        partN(input, true)
    }

    val input = object {}.javaClass.getResource("Day02.txt").readText()
    part1(input)
    part2(input)
}
