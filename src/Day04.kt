import java.io.File
import kotlin.math.pow

fun main() {

    fun List<String>.parse() = map { line ->
        val (winning, given) = line.split(":").last().split("|").map { parts ->
            parts.split(" ").mapNotNull { number -> number.trim().takeIf { it.isNotEmpty() } }
        }
        given.count { it in winning }
    }

    fun part1(input: List<String>) = input.parse().sumOf { if (it == 0) 0.0 else 2.0.pow(it - 1) }

    fun part2(input: List<String>): Int {
        val counts = input.parse()
        val copies = IntArray(counts.size) { 1 }

        counts.forEachIndexed { index, count ->
            for (i in 1..count) {
                copies[index + i] += copies[index]
            }
        }

        return copies.sum()
    }

    val input = File("src/Day04.txt").readLines()

    println(part1(input))
    println(part2(input))
}
