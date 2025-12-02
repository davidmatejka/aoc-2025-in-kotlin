fun main() {
    val dayNr = "02"
    fun part1(input: List<String>): Long {
        val ranges = input.first().split(",")
        var sum = 0L
        ranges.forEach {
            val (start, end) = it.split("-").map { it.toLong() }
            for (id in start..end) {
                val numString = id.toString()
                val len = numString.length
                if (numString.take(len / 2) == numString.substring(len/2, len)) {
                    sum += id
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val ranges = input.first().split(",")
        var sum = 0L
        ranges.forEach { range ->
            val (start, end) = range.split("-").map { num -> num.toLong() }
            for (id in start..end) {
                val numString = id.toString()
                for (i in 1..numString.length / 2) {
                    val seen = mutableSetOf<String>()
                    seen.addAll(numString.windowed(i,i, true))
                    if (seen.size == 1) {
                        sum += id
                        break
                    }
                }
            }
        }
        return sum
    }

    val testInput = readInput("Day${dayNr}_test")
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    val input = readInput("Day${dayNr}")
    part1(input).println()
    part2(input).println()
}
