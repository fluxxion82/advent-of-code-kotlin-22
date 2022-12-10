package aoc

import aoc.model.Coordinate
import aoc.model.moveDown
import aoc.model.moveLeft
import aoc.model.moveRight
import aoc.model.moveUp
import aoc.utils.readInput

fun getDirectionFromValue(directionValue: String): Direction? {
    return when (directionValue) {
        "R" -> Direction.RIGHT
        "L" -> Direction.LEFT
        "U" -> Direction.UP
        "D" -> Direction.DOWN
        else -> null
    }
}

fun main() {
    val testInput = readInput("Day09_test.txt")
    val testTwoInput = readInput("Day09_test_two.txt")
    val input = readInput("Day09.txt")

    fun part1(input: List<String>): Int {
        val directions = input.map {
            it.split(" ")
        }

        var head = Coordinate(0, 0)
        var tail = Coordinate(0, 0)
        val visited = mutableListOf(tail)
        directions.forEach { instruction ->
            val (direct, steps) = instruction

            for (step in 1 .. steps.toInt()) {
                head = when (getDirectionFromValue(direct)) {
                    Direction.UP -> head.moveUp()
                    Direction.RIGHT -> head.moveRight()
                    Direction.LEFT -> head.moveLeft()
                    Direction.DOWN -> head.moveDown()
                    null -> head
                }

                if (!head.isAdjacent(tail)) {
                    val foo = Coordinate(head.xCoord - tail.xCoord, head.yCoord - tail.yCoord)
                    tail += Coordinate(foo.xCoord.coerceIn(-1, 1), foo.yCoord.coerceIn(-1, 1))
                }

                visited.add(tail)
            }
        }

        return visited.toSet().size
    }


    fun part2(input: List<String>): Int {
        val directions = input.map {
            it.split(" ")
        }

        var tail = Coordinate(0, 0)
        val visited = mutableListOf(tail)
        val rope = (1..10).map { Coordinate(0, 0) }.toTypedArray()
        directions.forEach { instruction ->
            val (direct, steps) = instruction

            for (step in 1 .. steps.toInt()) {
                rope[0] = when (getDirectionFromValue(direct)) {
                    Direction.UP -> rope[0].moveUp()
                    Direction.RIGHT -> rope[0].moveRight()
                    Direction.LEFT -> rope[0].moveLeft()
                    Direction.DOWN -> rope[0].moveDown()
                    null -> rope[0]
                }

                for (knot in 1 until 10) {
                    val head = rope[knot - 1]
                    tail = rope[knot]
                    if (!head.isAdjacent(tail)) {
                        val foo = Coordinate(head.xCoord - tail.xCoord, head.yCoord - tail.yCoord)
                        rope[knot] += Coordinate(foo.xCoord.coerceIn(-1, 1), foo.yCoord.coerceIn(-1, 1))
                    }
                }
                visited.add(rope.last())
            }
        }

        return visited.toSet().size
    }


    println("part one test: ${part1(testInput)}") // 13
    println("part one: ${part1(input)}") // 6175

    println("part two test: ${part2(testInput)}") // 1
    println("part two test: ${part2(testTwoInput)}") // 36
    println("part two: ${part2(input)}") // 2578
}
