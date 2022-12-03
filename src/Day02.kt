import Result.Companion.getResultFromCode
import State.Companion.getStateFromCode
import java.io.File

enum class State(val codeOne: String, val codeTwo: String) {
    Rock("A", "X"),
    Paper("B", "Y"),
    Scissors("C", "Z");

    companion object {
        fun getStateFromCode(code: String): State? =
            values().find {
                it.codeOne == code || it.codeTwo == code
            }
    }
}

enum class Result(val code: String) {
    Winner("Z"),
    Loser("X"),
    Draw("Y");

    companion object {
        fun getResultFromCode(code: String): Result? =
            values().find { it.code == code }
    }
}

fun playerTwoResult(playerOne: State, playerTwo: State): Result {
    return when (playerTwo) {
        State.Rock -> when (playerOne) {
            State.Paper -> Result.Loser
            State.Scissors -> Result.Winner
            State.Rock -> Result.Draw
        }
        State.Paper -> when (playerOne) {
            State.Paper -> Result.Draw
            State.Scissors -> Result.Loser
            State.Rock -> Result.Winner
        }
        State.Scissors -> when (playerOne) {
            State.Paper -> Result.Winner
            State.Scissors -> Result.Draw
            State.Rock -> Result.Loser
        }
    }
}

fun getPointsForRound(result: Result): Int =
    when (result) {
        Result.Winner -> 6
        Result.Loser -> 0
        Result.Draw -> 3
    }

fun getPointsForChoice(choice: State): Int =
    when (choice) {
        State.Paper -> 2
        State.Scissors -> 3
        State.Rock -> 1
    }

fun playerTwoPlayFromResult(playerOne: State, playerTwo: Result): State {
    return when (playerTwo) {
        Result.Winner -> when (playerOne) {
            State.Paper -> State.Scissors
            State.Scissors -> State.Rock
            State.Rock -> State.Paper
        }
        Result.Loser -> when (playerOne) {
            State.Paper -> State.Rock
            State.Scissors -> State.Paper
            State.Rock -> State.Scissors
        }
        Result.Draw -> when (playerOne) {
            State.Paper -> State.Paper
            State.Scissors -> State.Scissors
            State.Rock -> State.Rock
        }
    }
}

fun main() {
    val testInput = File("src/Day02_test.txt").readText()
    val input = File("src/Day02.txt").readText()

    fun part1(input: String): Int =
        input.split("\n").sumOf {
            val round = it.split(" ")
            val myPlay = getStateFromCode(round.last())!!
            val opponentPlay = getStateFromCode(round.first())!!
            val meResult = playerTwoResult(opponentPlay, myPlay)

            getPointsForRound(meResult)+ getPointsForChoice(myPlay)
        }

    fun part2(input: String): Int =
        input.split("\n").sumOf {
            val round = it.split(" ")
            val opponentPlay = getStateFromCode(round.first())!!
            val meResult = getResultFromCode(round.last())!!
            val myPlay = playerTwoPlayFromResult(opponentPlay, meResult)

            getPointsForRound(meResult) + getPointsForChoice(myPlay)
        }


    println("part one test: ${part1(testInput)}")
    println("part one: ${part1(input)}")

    println("part two test: ${part2(testInput)}")
    println("part two: ${part2(input)}")
}