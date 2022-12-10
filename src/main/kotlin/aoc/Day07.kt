package aoc

import java.io.File

data class ElfFile(val name: String, val size: Long)

class Node(
    val file: ElfFile,
    val parent: Node?,
    val children: MutableList<Node> = mutableListOf()
) {
    val totalSize: Long = file.size + children.sumOf { it.totalSize }
}

fun parseTerminal(input: String, root: Node) {
    var currentNode: Node? = root

    input.split("\n").forEach {
        when {
            it.startsWith("\$") -> {
                if (it.startsWith("$ cd")) {
                    if (it.startsWith("$ cd ..")) {
                        currentNode = currentNode?.parent
                    } else {
                        val dir = it.substringAfter("$ cd")
                        if (dir != "/") {
                            currentNode?.children?.forEach { node ->
                                if (node.file.name == dir.trim()) {
                                    currentNode = node
                                }
                            }
                        }
                    }
                }
            }
            else -> {
                val (size, name) = it.split(" ")
                val fileSize = if (size == "dir") 0 else size.toLong()
                currentNode?.children?.add(Node(ElfFile(name, fileSize), currentNode))
            }
        }
    }
}

fun treeSum(root: Node?): Long {
    if (root == null) return 0
    return root.totalSize + root.children.sumOf { treeSum(it) }
}

fun streamNodes(node: Node): Sequence<Node> = sequence {
    yield((node))
    node.children.forEach { child ->
        yieldAll(streamNodes(child))
    }
}

fun getDirSizes(root: Node) =
    streamNodes(root).filter { it.file.size == 0L }.map { treeSum(it) }.toList().sorted()

fun main() {
    val testInput = File("src/Day07_test.txt").readText()
    val input = File("src/Day07.txt").readText()

    fun part1(input: String): Long {
        val disk = Node(ElfFile("/", 0), parent = null)
        parseTerminal(input, disk)

        val dirs = getDirSizes(disk)
        return dirs.takeWhile { it <= 100000L }.sum()
    }


    fun part2(input: String): Long {
        val disk = Node(ElfFile("/", 0), parent = null)
        parseTerminal(input, disk)

        val dirs = getDirSizes(disk)
        return dirs.first { it > dirs.last() - 40000000L }
    }


    println("part one test: ${part1(testInput)}") // 95437
    println("part one: ${part1(input)}") // 1770595

    println("part two test: ${part2(testInput)}") // 24933642
    println("part two: ${part2(input)}") // 2195372
}

