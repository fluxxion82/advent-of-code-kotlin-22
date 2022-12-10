package aoc.model

import kotlin.math.absoluteValue

data class Coordinate(val xCoord: Int, val yCoord: Int) {
    operator fun plus(other: Coordinate) = Coordinate(xCoord + other.xCoord, yCoord + other.yCoord)
    operator fun minus(other: Coordinate) = Coordinate(xCoord - other.xCoord, yCoord - other.yCoord)
    operator fun times(other: Int) = Coordinate(xCoord * other, yCoord * other)
    operator fun div(other: Int): Coordinate = Coordinate(xCoord / other, yCoord / other)

    fun isAdjacent(other: Coordinate) = this != other &&
            (xCoord-other.xCoord).absoluteValue <= 1 &&
            (yCoord-other.yCoord).absoluteValue <= 1
}

fun Coordinate.moveUp() = this + Coordinate(0, -1)
fun Coordinate.moveDown() = this + Coordinate(0, 1)
fun Coordinate.moveLeft() = this + Coordinate(-1, 0)
fun Coordinate.moveRight() = this + Coordinate(1, 0)
