package aoc

import aoc.utils.readInput

open class Day(
    file: String,
    useTest: Boolean,
) {
    val baseInput = if (useTest) readInput("${file}_test.txt") else readInput("$file.txt")

    // all the different ways to get your input
    val input: List<String> by lazy { baseInput }
    val inputAsGrid: List<List<Char>> by lazy { baseInput.map { it.toList() } }
    val inputAsInts: List<Int> by lazy { baseInput.map { it.extractInt() } }
    val inputAsLongs: List<Long> by lazy { baseInput.map { it.extractLong() } }
    val inputAsString: String by lazy { baseInput.joinToString("\n").also { listOf(it) } }

    var groupDelimiter: (String) -> Boolean = String::isEmpty
    val inputAsGroups: List<List<String>> by lazy { groupedInput(groupDelimiter) }

    fun groupedInput(delimiter: (String) -> Boolean): List<List<String>> {
        val result = mutableListOf<List<String>>()
        var currentSubList: MutableList<String>? = null
        for (line in baseInput) {
            if (delimiter(line)) {
                currentSubList = null
            } else {
                if (currentSubList == null) {
                    currentSubList = mutableListOf(line)
                    result += currentSubList
                } else
                    currentSubList.add(line)
            }
        }
        return result
    }

    fun <T> mappedInput(lbd: (String) -> T): List<T> =
        baseInput.map(catchingMapper(lbd))

    fun <T> parsedInput(
        columnSeparator: Regex = Regex("\\s+"),
        predicate: (String) -> Boolean = { true },
        lbd: ParserContext.(String) -> T,
    ): List<T> =
        baseInput.filter(predicate).map(parsingMapper(columnSeparator, lbd))

    fun <T> matchedInput(regex: Regex, lbd: (List<String>) -> T): List<T> =
        baseInput.map(matchingMapper(regex, lbd))

    companion object {

        private fun <T> matchingMapper(regex: Regex, lbd: (List<String>) -> T): (String) -> T = { s ->
            regex.matchEntire(s)?.groupValues?.let {
                runCatching { lbd(it) }.getOrElse { ex(s, it) }
            } ?: error("Input line does not match regex: \"$s\"")
        }

        private fun <T> catchingMapper(lbd: (String) -> T): (String) -> T = { s ->
            runCatching { lbd(s) }.getOrElse { ex(s, it) }
        }

        private fun <T> parsingMapper(columnSeparator: Regex, lbd: ParserContext.(String) -> T): (String) -> T = { s ->
            runCatching {
                ParserContext(columnSeparator, s).lbd(s)
            }.getOrElse { ex(s, it) }
        }

        private fun ex(input: String, ex: Throwable): Nothing =
            error("Exception on input line\n\n\"$input\"\n\n$ex")

        private fun <T> List<T>.preview(maxLines: Int, f: (idx: Int, data: T) -> Unit) {
            if (size <= maxLines) {
                forEachIndexed(f)
            } else {
                val cut = (maxLines - 1) / 2
                (0 until maxLines - cut - 1).forEach { f(it, this[it]!!) }
                if (size > maxLines) println("...")
                (lastIndex - cut + 1..lastIndex).forEach { f(it, this[it]!!) }
            }
        }

    }
}

class ParserContext(private val columnSeparator: Regex, private val line: String) {
    val cols: List<String> by lazy { line.split(columnSeparator) }
    val nonEmptyCols: List<String> by lazy { cols.filter { it.isNotEmpty() } }
    val nonBlankCols: List<String> by lazy { cols.filter { it.isNotBlank() } }
    val ints: List<Int> by lazy { line.extractAllIntegers() }
    val longs: List<Long> by lazy { line.extractAllLongs() }
}


private fun String.extractInt() = toIntOrNull() ?: sequenceContainedIntegers().first()
private fun String.extractLong() = toLongOrNull() ?: sequenceContainedLongs().first()

private val numberRegex = Regex("(-+)?\\d+")
private val positiveNumberRegex = Regex("\\d+")

fun String.sequenceContainedIntegers(includeNegativeNumbers: Boolean = true): Sequence<Int> =
    (if (includeNegativeNumbers) numberRegex else positiveNumberRegex).findAll(this)
        .mapNotNull { m -> m.value.toIntOrNull() ?: 0} // check

fun String.sequenceContainedLongs(includeNegativeNumbers: Boolean = true): Sequence<Long> =
    (if (includeNegativeNumbers) numberRegex else positiveNumberRegex).findAll(this)
        .mapNotNull { m -> m.value.toLongOrNull() ?: 0 } // check

fun String.extractAllIntegers(includeNegativeNumbers: Boolean = true): List<Int> =
    sequenceContainedIntegers(includeNegativeNumbers).toList()

fun String.extractAllLongs(includeNegativeNumbers: Boolean = true): List<Long> =
    sequenceContainedLongs(includeNegativeNumbers).toList()
