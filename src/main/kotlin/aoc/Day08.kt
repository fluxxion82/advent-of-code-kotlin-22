package aoc

import aoc.utils.readInput

data class Tree(val value: Int, var scenicValue: Int = 0, var isVisible: Boolean? = null)
enum class Direction {
    UP, RIGHT, LEFT, DOWN
}

fun main() {
    val testInput = readInput("Day08_test.txt")
    val input = readInput("Day08.txt")

    fun part1(input: List<String>): Int {
        val seq = input
        val grid = mutableListOf<List<Tree>>()
        seq.forEach {
            grid.add(
                it.toCharArray().map { char ->
                    Tree(char.digitToInt())
                }
            )
        }

        for (i in 0 until grid.size) {
            for (k in 0 until grid[0].size) {
                checkDirectionHeight(grid, i, k, Direction.RIGHT)
                checkDirectionHeight(grid, i, k, Direction.LEFT)
                checkDirectionHeight(grid, i, k, Direction.DOWN)
                checkDirectionHeight(grid, i, k, Direction.UP)
            }
        }

        return grid.sumOf { rows ->
            rows.count { tree ->
                tree.isVisible == true
            }
        }
    }

    fun part2(input: List<String>): Int {
        val seq = input
        val grid = mutableListOf<List<Tree>>()
        seq.forEach {
            grid.add(
                it.toCharArray().map { char ->
                    Tree(char.digitToInt())
                }
            )
        }

        var scenicValue = 0
        for (i in 0 until grid.size) {
            for (k in 0 until grid[0].size) {
                var first = checkDirectionScenic(grid, i, k, Direction.RIGHT)
                first *= checkDirectionScenic(grid, i, k, Direction.LEFT)
                first *= checkDirectionScenic(grid, i, k, Direction.DOWN)
                first *= checkDirectionScenic(grid, i, k, Direction.UP)

                if (first > scenicValue) scenicValue = first
            }
        }

        return scenicValue
    }


    println("part one test: ${part1(testInput)}") // 21
    println("part one: ${part1(input)}") // 1695

    println("part two test: ${part2(testInput)}") // 8
    println("part two: ${part2(input)}") // 287040
}

fun decreaseRowScanHeight(grid: MutableList<List<Tree>>, column: Int) {
    var height = -1
    for (i in grid.size- 1 downTo 0) {
        val curr = grid[i][column]
        if (curr.value > height) {
            curr.isVisible = true
            height = curr.value
        }
    }
}

fun increaseRowScanHeight(grid: MutableList<List<Tree>>, column: Int) {
    var height = -1
    for (i in 0 until grid[0].size) {
        val curr = grid[i][column]
        if (curr.value > height) {
            curr.isVisible = true
            height = curr.value
        }
    }
}

fun increaseColumnIndexScanHeight(grid: MutableList<List<Tree>>, row: Int) {
    var height = -1
    for (i in 0 until grid[0].size) {
        val curr = grid[row][i]
        if (curr.value > height) {
            curr.isVisible = true
            height = curr.value
        }
    }
}

fun decreaseColumnScanHeight(grid: MutableList<List<Tree>>, row: Int) {
    var height = -1
    for (i in grid.size- 1 downTo 0) {
        val curr = grid[row][i]
        if (curr.value > height) {
            curr.isVisible = true
            height = curr.value
        }
    }
}

fun checkDirectionHeight(grid: MutableList<List<Tree>>, row: Int, column: Int, direction: Direction) {
    when(direction) {
        Direction.DOWN -> increaseRowScanHeight(grid, column)
        Direction.UP-> decreaseRowScanHeight(grid, column)
        Direction.RIGHT -> increaseColumnIndexScanHeight(grid, row)
        Direction.LEFT -> decreaseColumnScanHeight(grid, row)
    }
}

fun decreaseRowScanScenic(grid: MutableList<List<Tree>>, row: Int, column: Int): Int {
    var scenicValue = row
    for (i in row - 1 downTo 0) {
        val next = grid[i][column]
        val curr = grid[row][column]
        if (next.value >= curr.value) {
            scenicValue = row - i
            break
        }
    }

    return scenicValue
}

fun increaseRowScanScenic(grid: MutableList<List<Tree>>, row: Int, column: Int): Int {
    var scenicValue = grid[0].size - row - 1
    for (i in row + 1 until grid.size) {
        val next = grid[i][column]
        val curr = grid[row][column]
        if (next.value >= curr.value) {
            scenicValue = i - row
            break
        }
    }
    return scenicValue
}

fun decreaseColumnScanScenic(grid: MutableList<List<Tree>>, row: Int, column: Int): Int {
    var scenicValue = column
    for (i in column - 1 downTo 0) {
        val next = grid[row][i]
        val curr = grid[row][column]
        if (next.value >= curr.value) {
            scenicValue = column - i
            break
        }
    }

    return scenicValue
}

fun increaseColumnIndexScanScenic(grid: MutableList<List<Tree>>, row: Int, column: Int): Int {
    var scenicValue = grid.size - column - 1
    for (i in column + 1  until grid.size) {
        val next = grid[row][i]
        val curr = grid[row][column]
        if (next.value >= curr.value) {
            scenicValue = i - column
            break
        }
    }

    return scenicValue
}

fun checkDirectionScenic(grid: MutableList<List<Tree>>, row: Int, column: Int, direction: Direction): Int {
    return when(direction) {
        Direction.DOWN -> decreaseRowScanScenic(grid, row, column)
        Direction.UP-> increaseRowScanScenic(grid, row, column)
        Direction.RIGHT -> increaseColumnIndexScanScenic(grid, row, column)
        Direction.LEFT -> decreaseColumnScanScenic(grid, row, column)
    }
}
