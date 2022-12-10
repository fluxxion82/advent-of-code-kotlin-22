package aoc.utils

import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> =
    with(
        object{}.javaClass.getResourceAsStream(name)
        ?: ClassLoader.getSystemClassLoader().getResourceAsStream(name)
    ) {
        return bufferedReader(Charsets.UTF_8).readLines()
    }

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
