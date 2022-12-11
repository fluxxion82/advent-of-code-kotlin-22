package aoc.utils

fun Iterable<Long>.product(): Long = reduce(Long::safeTimes)
fun Sequence<Long>.product(): Long = reduce(Long::safeTimes)

@JvmName("intProduct")
fun Iterable<Int>.product(): Long = fold(1L, Long::safeTimes)

@JvmName("intProduct")
fun Sequence<Int>.product(): Long = fold(1L, Long::safeTimes)

infix fun Int.safeTimes(other: Int) = (this * other).also {
    check(other == 0 || it / other == this) { "Integer Overflow at $this * $other" }
}

infix fun Long.safeTimes(other: Long) = (this * other).also {
    check(other == 0L || it / other == this) { "Long Overflow at $this * $other" }
}

infix fun Long.safeTimes(other: Int) = (this * other).also {
    check(other == 0 || it / other == this) { "Long Overflow at $this * $other" }
}

infix fun Int.safeTimes(other: Long) = (this.toLong() * other).also {
    check(other == 0L || it / other == this.toLong()) { "Long Overflow at $this * $other" }
}

fun Long.checkedToInt(): Int = let {
    check(it in Int.MIN_VALUE..Int.MAX_VALUE) { "Value does not fit in Int: $it" }
    it.toInt()
}
