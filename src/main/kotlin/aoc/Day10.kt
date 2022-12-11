package aoc

import aoc.utils.readInput
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.Warmup


open class CPU {
    var register: Int = 1
    val clock = AtomicInteger()
    var counter = 0

    fun addx(value: Int) {
        clock.getAndIncrement()
        check()
        clock.getAndIncrement()
        check()
        register += value
    }

    fun noOp() {
        clock.getAndIncrement()
        check()
    }

    open fun check() {
        if (clock.get() % 40 - 20 == 0) {
            counter += register * clock.get()
        }
        crt()
    }

    fun crt() {
        if ((clock.get() - 1) % 40 == 0) {
            println()
        } else {
            val index = (clock.get() - 1) % 40
            if (index in register - 1 .. register + 1) { // sprint onscreen
                print("#")
            } else {
                print(".")
            }
        }
    }
}

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 2)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
class Day10(
    val testInput: Boolean = false
) {
    private var input = emptyList<String>()

    @Setup
    fun setup() {
        input = if (testInput) readInput("Day10_test.txt") else readInput("Day10.txt")
    }

    @Benchmark
    fun part1(): Int {
        val cpu = CPU()
        input.forEach { cmd ->
            val split = cmd.split(" ")
            if (split.size == 1) {
                cpu.noOp()
            } else {
                val (_, valu) = split
                cpu.addx(valu.toInt())
            }
        }

        return cpu.counter
    }

    @Benchmark
    fun part2() {
        val cpu = CPU()
        input.forEach { cmd ->
            val split = cmd.split(" ")
            if (split.size == 1) {
                cpu.noOp()
            } else {
                val (_, valu) = split
                cpu.addx(valu.toInt())
            }
        }

        println()
    }
}

fun main() {
    val day10 = Day10(true)

    // println("part one test: ${day10.part1()}") // 13140
    println("part one: ${day10.part1()}") // 15140

    // day10.part2()
    day10.part2() // BPJAZGAP
}
