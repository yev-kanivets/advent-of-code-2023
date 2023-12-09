import java.io.File

fun main() {

    fun List<String>.parse() = map { line ->
        line.split(":").last()
            .split(" ")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { it.toLong() }
    }.let { (time, distance) ->
        time.mapIndexed { index, value ->
            value to distance[index]
        }
    }

    fun List<Pair<Long, Long>>.solve() = fold(1) { acc, (time, distance) ->
        acc * (0..time).count { it * (time - it) > distance }
    }

    fun part1(input: List<String>) = input.parse().solve()

    fun part2(input: List<String>) = input
        .map { line -> line.filter { it != ' ' } }
        .parse()
        .solve()

    val input = File("src/Day06.txt").readLines()

    println(part1(input))
    println(part2(input))
}
