package aoc

import aoc.utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        val data = input.filterNot { it == "" } .map { elf ->
            elf.lines().map { it.toInt() }
        }

        return data.maxOf { it.sum() }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01")
    // check(part1(testInput) == 1)

    val input = readInput("Day01.txt")

    data class Elf(val id: Int, val calories: MutableList<Int> = mutableListOf()) {
        fun total() = calories.sumOf { it }
    }

    var elf = Elf(0)
    val elves = mutableListOf<Elf>()

    input.forEach { calorie ->
        if (calorie == "") {
            elves.add(elf)
            elf = Elf(elves.size)
        } else {
            elf.calories.add(calorie.toInt())
        }
    }

    elves.sortByDescending { it.total() }
    val winner = elves.first()
    println("elf ${winner.id} with ${winner.total()}")

    val newInput = readInput("Day01.txt")
    println(part1(newInput)) // 69836

    val topThree = elves.take(3).sumOf { it.total() }
    println("top three total: $topThree") // 207968
}
