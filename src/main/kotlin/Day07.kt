fun main(args: Array<String>) {
    val TOTAL_DISK_SPACE = 70000000
    val UNUSED_SPACE_NEEDED = 30000000

    abstract class Node(val name: String, var size: Int) {
        abstract fun calcSize(): Int
        abstract fun sumMatchingSizes(): Int
    }

    class File(name: String, size: Int): Node(name, size) {
        override fun calcSize(): Int {
            return size
        }

        override fun sumMatchingSizes(): Int {
            return 0
        }
    }

    class Dir(name: String, val parent: Dir?): Node(name, 0) {
        val children = mutableMapOf<String, Node>()

        override fun calcSize(): Int {
            size = children.values.sumOf { it.calcSize() }
            return size
        }

        override fun sumMatchingSizes(): Int {
            return (if (size <= 100000) size else 0) + children.values.sumOf { it.sumMatchingSizes() }
        }

        fun findSmallestDirWithMinSize(minSize: Int): Dir? {
            val me = if (size >= minSize) this else null
            val descendantCandidates = children.values.mapNotNull {
                if (it !is Dir) null else it.findSmallestDirWithMinSize(minSize)
            }
            return (listOfNotNull(me) + descendantCandidates).minWithOrNull(compareBy { it.size })
        }
    }

    fun executeLine(cwd: Dir, lines: List<String>, startLineNum: Int): Pair<Dir, Int> {
        var lineNum = startLineNum
        val tokens = lines[lineNum++].split(" ")
        if (tokens[0] != "$") {
            throw Exception("Expected prompt, got ${tokens[0]}")
        }
        when (tokens[1]) {
            "cd" -> {
                val targetDir = tokens[2]
                when (targetDir) {
                    "/" -> {
                        if (cwd.parent != null) {
                            throw Exception("unexpected cd to root after start")
                        }
                        // else ignore
                    }
                    ".." -> {
                        return Pair(cwd.parent!!, lineNum)
                    }
                    else -> {
                        // assume "cd X" never precedes the ls output for its parent
                        return Pair(cwd.children.getValue(targetDir) as Dir, lineNum)
                    }
                }
            }
            "ls" -> {
                if (cwd.children.isNotEmpty()) {
                    throw Exception("already listed ${cwd.name}")
                } else {
                    while (lineNum < lines.size
                        && lines[lineNum].isNotEmpty()
                        && !lines[lineNum].startsWith("$")
                    ) {
                        val (dirOrSize, name) = lines[lineNum++].split(" ")
                        val child = if (dirOrSize == "dir") {
                            Dir(name, cwd)
                        } else {
                            File(name, dirOrSize.toInt())
                        }
                        if (cwd.children.containsKey(name))
                            throw Exception("$name already exists")
                        cwd.children[name] = child
                    }
                }
                return Pair(cwd, lineNum)
            }
            else -> {
                throw Exception("bad token ${tokens[1]}")
            }
        }
        return Pair(cwd, lineNum)
    }

    fun loadFileSystem(input: String): Dir {
        val lines = input.split("\n")
        val root = Dir("/", null)
        var cwd = root
        var lineNum = 0
        while (lineNum < lines.size && lines[lineNum].isNotEmpty()) {
            val res = executeLine(cwd, lines, lineNum)
            cwd = res.first
            lineNum = res.second
        }

        root.calcSize()
        return root
    }

    fun part1(root: Dir) {
        println("Part 1 sum of matching sizes: " + root.sumMatchingSizes())
    }

    fun part2(root: Dir) {
        val freeSpace = TOTAL_DISK_SPACE - root.size
        val spaceNeeded = UNUSED_SPACE_NEEDED - freeSpace
        if (spaceNeeded <= 0) {
            throw Exception("There's already enough free space")
        }
        println("Needed space: $spaceNeeded")
        val toDelete = root.findSmallestDirWithMinSize(spaceNeeded)
        println("Dir to delete: ${toDelete!!.name} ${toDelete!!.size}")
    }

    val input = object {}.javaClass.getResource("Day07.txt").readText()

    val root = loadFileSystem(input)
    part1(root)
    part2(root)
}
