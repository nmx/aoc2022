fun main(args: Array<String>) {
    class Tree(val height: Int) {
        var visited = false
        var viewScore = 0

        override fun toString(): String {
            return "$height${if (visited) '*' else ' '} "
        }
    }

    fun countRows(input: List<String>): Int {
        return input.size
    }

    fun countCols(input: List<String>): Int {
        return input[0].length
    }

    fun visit(tree: Tree, maxHeight: Int): Int {
        return if (tree.height > maxHeight) {
            tree.visited = true
            tree.height
        } else {
            maxHeight
        }
    }

    fun countFromTop(grid: Array<Array<Tree>>) {
        for (col in 0 until grid[0].size) {
            var maxHeight = -1
            for (row in 0 until grid.size) {
                maxHeight = visit(grid[row][col], maxHeight)
            }
        }
    }

    fun countFromRight(grid: Array<Array<Tree>>) {
        for (row in 0 until grid.size) {
            var maxHeight = -1
            for (col in grid.size - 1 downTo 0) {
                maxHeight = visit(grid[row][col], maxHeight)
            }
        }
    }

    fun countFromBottom(grid: Array<Array<Tree>>) {
        for (col in 0 until grid[0].size) {
            var maxHeight = -1
            for (row in grid.size - 1 downTo 0) {
                maxHeight = visit(grid[row][col], maxHeight)
            }
        }
    }

    fun countFromLeft(grid: Array<Array<Tree>>) {
        for (row in 0 until grid.size) {
            var maxHeight = -1
            for (col in 0 until grid.size) {
                maxHeight = visit(grid[row][col], maxHeight)
            }
        }
    }

    fun part1(grid: Array<Array<Tree>>) {
        countFromTop(grid)
        countFromRight(grid)
        countFromBottom(grid)
        countFromLeft(grid)

        grid.forEach{
            it.forEach { it2 -> print(it2)}
            println()
        }

        val visibleCount = grid.sumOf { it.filter { tree -> tree.visited }.size }
        println("Visible: $visibleCount")
    }

    fun viewScoreUp(grid: Array<Array<Tree>>, treeRow: Int, treeCol: Int): Int {
        val height = grid[treeRow][treeCol].height
        var score = 0
        for (row in treeRow - 1 downTo 0) {
            score++
            if (grid[row][treeCol].height >= height) {
                break
            }
        }
        return score
    }

    fun viewScoreRight(grid: Array<Array<Tree>>, treeRow: Int, treeCol: Int): Int {
        val height = grid[treeRow][treeCol].height
        var score = 0
        for (col in treeCol + 1 until grid[treeRow].size) {
            score++
            if (grid[treeRow][col].height >= height) {
                break
            }
        }
        return score
    }

    fun viewScoreDown(grid: Array<Array<Tree>>, treeRow: Int, treeCol: Int): Int {
        val height = grid[treeRow][treeCol].height
        var score = 0
        for (row in treeRow + 1 until grid.size) {
            score++
            if (grid[row][treeCol].height >= height) {
                break
            }
        }
        return score
    }

    fun viewScoreLeft(grid: Array<Array<Tree>>, treeRow: Int, treeCol: Int): Int {
        val height = grid[treeRow][treeCol].height
        var score = 0
        for (col in treeCol - 1 downTo 0) {
            score++
            if (grid[treeRow][col].height >= height) {
                break
            }
        }
        return score
    }

    fun calcViewScore(grid: Array<Array<Tree>>, row: Int, col: Int) {
        grid[row][col].viewScore = viewScoreUp(grid, row, col)
        if (grid[row][col].viewScore == 0) return

        grid[row][col].viewScore *= viewScoreRight(grid, row, col)
        if (grid[row][col].viewScore == 0) return

        grid[row][col].viewScore *= viewScoreDown(grid, row, col)
        if (grid[row][col].viewScore == 0) return

        grid[row][col].viewScore *= viewScoreLeft(grid, row, col)
    }

    fun part2(grid: Array<Array<Tree>>) {
        for (row in 0 until grid.size) {
            for (col in 0 until grid[row].size) {
                calcViewScore(grid, row, col)
            }
        }

        grid.forEach{
            it.forEach { it2 -> print("${it2.viewScore} ") }
            println()
        }

        val bestViewScore = grid.maxOf { it -> it.maxOf { tree -> tree.viewScore }}
        println("Best view score: $bestViewScore")
    }

    val input = object {}.javaClass.getResource("Day08.txt").readText().trim().split("\n")

    val rows = countRows(input)
    val cols = countCols(input)
    val protoGrid: Array<Array<Tree?>> = Array(rows) {
        arrayOfNulls(cols)
    }
    input.forEachIndexed { rowIdx, row -> row.forEachIndexed {
            colIdx, height -> protoGrid[rowIdx][colIdx] = Tree(height.digitToInt()) } }
    val grid = protoGrid as Array<Array<Tree>>

    part1(grid)
    part2(grid)
}
