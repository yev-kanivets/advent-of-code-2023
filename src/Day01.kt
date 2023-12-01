import java.io.File

fun main() {

    val regularDigits = (1..9).associateBy { "$it" }
    val spelledDigits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine").mapIndexed { index, digit -> digit to index + 1 }

    fun List<String>.calibrate(digits: Map<String, Int>) = sumOf { line ->
        val firstDigit = line.findAnyOf(digits.keys)?.second
        val lastDigit = line.findLastAnyOf(digits.keys)?.second
        "${digits[firstDigit]}${digits[lastDigit]}".toIntOrNull() ?: 0
    }

    fun part1(input: List<String>) = input.calibrate(regularDigits)

    fun part2(input: List<String>) = input.calibrate(regularDigits + spelledDigits)

    val input = File("src/Day01.txt").readLines()

    println(part1(input))
    println(part2(input))
}
