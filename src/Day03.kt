import java.io.File

fun main() {

    fun Pair<Int, Int>.adjacentCells(
        maxFirst: Int,
        maxSecond: Int,
    ) = listOf(
        first to second - 1,
        first to second + 1,
        first - 1 to second,
        first - 1 to second - 1,
        first - 1 to second + 1,
        first + 1 to second,
        first + 1 to second - 1,
        first + 1 to second + 1,
    ).filter { it.first in 0..maxFirst && it.second in 0..maxSecond }

    data class Number(
        val y: Int,
        val xStart: Int,
        val xEnd: Int,
        val value: Int,
    ) {

        fun adjacentCells(
            maxY: Int,
            maxX: Int,
        ) = (xStart..xEnd).map { x -> Pair(y, x).adjacentCells(maxY, maxX) }.flatten().distinct()
    }

    fun List<String>.parseNumbers() = mapIndexed { index, line ->
        Regex("\\d+").findAll(line).map { matchResult ->
            Number(
                y = index,
                xStart = matchResult.range.first,
                xEnd = matchResult.range.last,
                value = matchResult.value.toInt()
            )
        }.toList()
    }.flatten()

    fun List<String>.parseAsterisks() = mapIndexed { index, line ->
        Regex("\\*").findAll(line).map { it -> index to it.range.first }.toList()
    }.flatten()

    fun part1(input: List<String>) = input.parseNumbers().sumOf { number ->
        val adjacentCells = number.adjacentCells(maxY = input.lastIndex, maxX = input.first().lastIndex)
        val isPartNumber = adjacentCells.any { (y, x) ->
            !input[y][x].isDigit() && input[y][x] != '.'
        }
        if (isPartNumber) number.value else 0
    }

    fun part2(input: List<String>): Int {
        val numbers = input.parseNumbers()
        val asterisks = input.parseAsterisks()

        return asterisks.sumOf { asterisk ->
            val adjacentCells = asterisk.adjacentCells(maxFirst = input.lastIndex, maxSecond = input.first().lastIndex)
            val adjacentNumbers = numbers.filter { number ->
                adjacentCells.any { it.first == number.y && it.second in number.xStart..number.xEnd }
            }
            if (adjacentNumbers.size == 2) adjacentNumbers.first().value * adjacentNumbers.last().value else 0
        }
    }

    val input = File("src/Day03.txt").readLines()

    println(part1(input))
    println(part2(input))
}
