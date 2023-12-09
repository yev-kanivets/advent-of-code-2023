import java.io.File

fun main() {

    fun List<String>.parse() = joinToString(separator = " ")
        .filter { it.isDigit() || it.isWhitespace() || it == ':' }
        .split(":")
        .filter { it.isNotEmpty() }
        .map { it.trim().split(" ").map { it.toLong() } }
        .let { numbers ->
            val seeds = numbers.first()
            val transforms = numbers.drop(1).map { map ->
                map.chunked(3).map { it[1] until it[1] + it[2] to (it[0] until it[0] + it[2]) }
            }
            seeds to transforms
        }

    fun Pair<List<LongRange>, List<List<Pair<LongRange, LongRange>>>>.solve() = let { (seeds, transforms) ->
        seeds.minOf { seedRange ->
            seedRange.minOf { seed ->
                var transformedSeed = seed
                transforms.forEach { transform ->
                    transform
                        .firstOrNull { transformedSeed in it.first }
                        ?.let { transformedSeed = transformedSeed - it.first.first + it.second.first }
                }
                transformedSeed
            }
        }
    }

    fun part1(input: List<String>) = input.parse()
        .let { (seeds, transforms) -> seeds.map { it..it } to transforms }
        .solve()

    fun part2(input: List<String>) = input.parse()
        .let { (seeds, transforms) -> seeds.chunked(2).map { (it[0] until (it[0] + it[1])) } to transforms }
        .solve()

    val input = File("src/Day05.txt").readLines()

    println(part1(input))
    println(part2(input))
}
