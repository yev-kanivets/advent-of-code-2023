import java.io.File

fun main() {

    fun List<String>.parse(color: String) = firstOrNull { it.contains(color) }?.substringBefore(" ")?.toInt() ?: 0

    fun List<String>.parse() = map { line ->
        line.split(":").last().split(";").map { part ->
            val subset = part.split(",").map { it.trim() }
            val red = subset.parse(color = "red")
            val green = subset.parse(color = "green")
            val blue = subset.parse(color = "blue")
            Triple(red, green, blue)
        }
    }

    fun part1(input: List<String>) = input.parse().mapIndexed { index, game ->
        game.forEach { subset ->
            if (subset.first > 12 || subset.second > 13 || subset.third > 14) return@mapIndexed 0
        }
        return@mapIndexed index + 1
    }.sum()

    fun part2(input: List<String>) = input.parse().sumOf { game ->
        val minRed = game.maxOf { it.first }
        val minGreen = game.maxOf { it.second }
        val minBlue = game.maxOf { it.third }
        return@sumOf minRed * minGreen * minBlue
    }

    val input = File("src/Day02.txt").readLines()

    println(part1(input))
    println(part2(input))
}
