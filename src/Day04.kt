import java.io.File

fun List<String>.rangeValueStart() = this.first().toInt()
fun List<String>.rangeValueEnd() = this.last().toInt()
fun List<String>.getRange() = (this.rangeValueStart() .. this.rangeValueEnd())

fun main() {
    val testInput = File("src/Day04_test.txt").readText()
    val input = File("src/Day04.txt").readText()
    
    fun part1(input: String): Int {
        return input.split("\n")
            .map {
                it.split(",")
            }.associate { pairs ->
                pairs.first().split("-") to pairs.last().split("-")
            }.count {
                val firstRange = it.key
                val secondRange = it.value

                firstRange.getRange().contains(secondRange.rangeValueStart()) &&
                        firstRange.getRange().contains(secondRange.rangeValueEnd()) ||
                        secondRange.getRange().contains(firstRange.rangeValueStart()) &&
                        secondRange.getRange().contains(firstRange.rangeValueEnd())

            }
    }


    fun part2(input: String): Int {
        return input.split("\n")
            .map {
                it.split(",")
            }.associate { pairs ->
                pairs.first().split("-") to pairs.last().split("-")
            }.count {
                val firstRange = it.key
                val secondRange = it.value

                firstRange.getRange().contains(secondRange.rangeValueStart()) &&
                        firstRange.getRange().contains(secondRange.rangeValueEnd()) ||
                        secondRange.getRange().contains(firstRange.rangeValueStart()) ||
                        secondRange.getRange().contains(firstRange.rangeValueEnd())
            }
    }


    println("part one test: ${part1(testInput)}")
    println("part one: ${part1(input)}")

    println("part two test: ${part2(testInput)}")
    println("part two: ${part2(input)}")
}
