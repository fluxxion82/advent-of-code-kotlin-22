package aoc

import java.io.File

fun main() {
    val testInput = File("src/Day06_test.txt").readText()
    val testInputTwo = File("src/Day06_test_two.txt").readText()
    val input = File("src/Day06.txt").readText()

    fun part1(input: String): Int {
        val seq = input.toCharArray()

        return seq.asSequence().windowed(4).indexOfFirst { chars ->
            val set = chars.toSet()
            set.size == 4
        } + 4
    }


    fun part2(input: String): Int {
        val seq = input.toCharArray()

        return seq.asSequence().windowed(14).indexOfFirst { chars ->
            val set = chars.toSet()
            set.size == 14
        } + 14
    }


    println("part one test: ${part1(testInput)}") // jpqm
    println("part one test two: ${part1(testInputTwo)}") // jpqm
    println("part one: ${part1(input)}")

    //println("part two test: ${part2(testInput)}")
    println("part two test two: ${part2(testInputTwo)}")
    println("part two: ${part2(input)}")
}
