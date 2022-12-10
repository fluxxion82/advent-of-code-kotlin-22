package aoc

import java.io.File
import java.util.Stack

fun main() {
    val testInput = File("src/Day05_test.txt").readText()
    val input = File("src/Day05.txt").readText()

    fun part1(input: String): String {
        val splitInput = input.split("\n")

        val crates = splitInput
            .takeWhile { it.isNotEmpty() }
            .dropLast(1)
            .map {
                it.chunked(4)
            }

        val stacks = crates
            .maxBy { it.size }
            .map {
                Stack<String>()
            }

        crates.forEachIndexed { _, createRow ->
            createRow.forEachIndexed { stackNumber, id ->
                val crate = id.filter(Char::isLetter)
                if (crate.isNotEmpty()) {
                    stacks.getOrNull(stackNumber)?.add(0, crate)
                }
            }
        }

        splitInput
            .filter { it.startsWith("move") }
            .forEach {
                val amount = it.substringAfter("move ").substringBefore(" ")
                val startStack = it.substringAfter("from ").substringBefore(" ").toInt()
                val endStack = it.substringAfter("to ").substringBefore("\n").toInt()

                for (i in 0 until amount.toInt()) {
                    val crate = stacks[startStack - 1].pop()
                    stacks[endStack - 1].push(crate)
                }
            }

        return stacks.reversed().foldRight("") { stack, initial ->
            "$initial${stack.lastElement()}"
        }
    }

    fun part2(input: String): String {
        val splitInput = input.split("\n")

        val crates = splitInput
            .takeWhile { it.isNotEmpty() }
            .dropLast(1)
            .map {
                it.chunked(4)
            }

        val stacks = crates
            .maxBy { it.size }
            .map {
                Stack<String>()
            }

        crates.forEachIndexed { _, createRow ->
            createRow.forEachIndexed { stackNumber, id ->
                val crate = id.filter(Char::isLetter)
                if (crate.isNotEmpty()) {
                    stacks.getOrNull(stackNumber)?.add(0, crate)
                }
            }
        }

        splitInput
            .filter { it.startsWith("move") }
            .forEach {
                val amount = it.substringAfter("move ").substringBefore(" ")
                val startStack = it.substringAfter("from ").substringBefore(" ").toInt()
                val endStack = it.substringAfter("to ").substringBefore("\n").toInt()

                val tempCrates = mutableListOf<String>()
                for (i in 0 until amount.toInt()) {
                    val crate = stacks[startStack - 1].pop()
                    tempCrates.add(crate)
                }

                tempCrates.reverse()
                tempCrates.forEach { crate ->
                    stacks[endStack - 1].push(crate)
                }
            }

        return stacks.reversed().foldRight("") { stack, initial ->
            "$initial${stack.lastElement()}"
        }
    }

    println("part one test: ${part1(testInput)}")
    println("part one: ${part1(input)}")

    println("part two test: ${part2(testInput)}")
    println("part two: ${part2(input)}")
}
