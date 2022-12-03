import java.io.File

fun main() {
    fun part1(input: String): Int {
        val data = input.split("\n\n").map { elf ->
            elf.lines().map { it.toInt() }
        }

        return data.maxOf { it.sum() }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01")
    // check(part1(testInput) == 1)

    val input = readInput("Day01")

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

    val newInput = File("src/Day01.txt").readText()
    println(part1(newInput))

    val topThree = elves.take(3).sumOf { it.total() }
    println("top three total: $topThree")
}
