import java.io.File

fun main() {
    val testInput = File("src/Day03_test.txt").readText()
    val input = File("src/Day03.txt").readText()

    fun Char.getPriority(): Int =
        if (this.isUpperCase()) {
            this.code - 'A'.code + 27
        } else {
            this.code - 'a'.code + 1
        }
    
    fun part1(input: String): Int =
        input.split("\n").sumOf {
            val compartments = it.toList().chunked(it.length/2)
            val (firstCompart, secondCompart) = compartments
            (firstCompart intersect secondCompart).single().getPriority()
        }

    fun part2(input: String): Int =
        input.split("\n").chunked(3).sumOf {
            val (one, two, three) = it
            (one.toSet() intersect two.toSet() intersect three.toSet())
                .single()
                .getPriority()
        }


    println("part one test: ${part1(testInput)}")
    println("part one: ${part1(input)}")

    println("part two test: ${part2(testInput)}")
    println("part two: ${part2(input)}")
}
