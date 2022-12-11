package aoc

import aoc.utils.lcm
import java.util.Stack
import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup

interface WorryUpdater {
    fun Long.updateWorryLevel(): Long
}

class DivisionUpdater(private val divisor: Int) : WorryUpdater {
    override fun Long.updateWorryLevel(): Long = this / divisor
}

class ModulusUpdater(private val modulus: Int) : WorryUpdater {
    override fun Long.updateWorryLevel(): Long = this % modulus
}

class Monkey(
    private val items: Stack<Long>,
    private val operation:(Long)-> Long,
    private val testOp:Int = 0,
    private val friends: List<Int>,
) {
    var numOfInspections = 0L

    private fun catchThrown(item: Long){
        items.add(item)
    }

    context (WorryUpdater)
    fun test(monkeys: List<Monkey>) {
        numOfInspections += items.count()
        val iterator = items.iterator()
        while(iterator.hasNext()) {
            val item = items.pop()
            val newWorryVal = operation(item).updateWorryLevel()
            if (newWorryVal % testOp == 0L) {
                monkeys[friends.first()].catchThrown(newWorryVal)
            } else {
                monkeys[friends.last()].catchThrown(newWorryVal)
            }
        }
    }
}


@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 2)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
class Day11(
    val testInput: Boolean = false
): Day(
    file = "Day11",
    useTest = testInput
) {
    var monkeys = listOf<Monkey>()
    var commonMod = 0

    @Setup
    fun setup () {
    }

    private fun createOperation(s: String): (Long) -> Long =
        s.split(" ").let { (_, operator, op2) ->
            when (operator) {
                "+" -> { old -> old + (op2.toLongOrNull() ?: old) }
                "*" -> { old -> old * (op2.toLongOrNull() ?: old) }
                else -> error(s)
            }
        }

    fun partSetup() {
        val divisibles = mutableListOf<Int>()
        monkeys = inputAsGroups.map { group ->
            val (starting, oper, testNum, friendOne, friendTwo) = group.drop(1)
            val divisor = testNum.extractAllIntegers().single()
            divisibles.add(divisor)

            Monkey(
                Stack<Long>().apply {  addAll(starting.extractAllLongs() ) },
                createOperation(oper.substringAfter("new = ")),
                divisor,
                friends = listOf(
                    friendOne.extractAllIntegers().single(),
                    friendTwo.extractAllIntegers().single(),
                )
            )
        }

        commonMod = divisibles.lcm().toInt()
    }

    @Benchmark
    fun part1(): Long {
        partSetup()

        with(DivisionUpdater(3)) {
            repeat(20) {
                for (monkey in monkeys) {
                    monkey.test(monkeys)
                }
            }
        }

        return monkeys.map { it.numOfInspections }.sorted().takeLast(2).reduce { a, b -> a * b }
    }

    @Benchmark
    fun part2(): Long {
        partSetup()

        with(ModulusUpdater(commonMod)) {
            repeat(10000) {
                for (monkey in monkeys) {
                    monkey.test(monkeys)
                }
            }
        }
        return monkeys.map { it.numOfInspections }.sorted().takeLast(2).reduce { a, b -> a * b }
    }
}

fun main() {
    val day11Test = Day11(true)

    with (day11Test) {
        println("part one test: ${part1()}") // 10605
        println("part two test: ${part2()}") // 2713310158
    }

    val day11 = Day11(false)

    with (day11) {
        println("part one: ${part1()}") // 51075
        println("part two: ${part2()}") // 11741456163
    }
}
