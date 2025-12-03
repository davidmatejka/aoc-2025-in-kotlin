fun main() {
    val dayNr = "03"

    fun getLargestNumber(bank: String, digits: Int): Long {
        var copy = bank.toMutableList().map { it.digitToInt() }
        val erg = mutableListOf<Int>()
        for (i in digits - 1 downTo 0) {
            val head = copy.dropLast(i)
            val max = head.max()
            val maxIndex = head.indexOf(max)
            erg.add(max)
            copy = copy.drop(maxIndex + 1)
        }
        return erg.joinToString("").toLong()
    }

    fun part1(input: List<String>): Long {
        var sum = 0L
        input.forEach { bank ->
            sum += getLargestNumber(bank, 2)
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        var sum = 0L
        input.forEach { bank ->
            sum += getLargestNumber(bank, 12)
        }
        return sum
    }

    val testInput = readInput("Day${dayNr}_test")
    check(part1(testInput) == 357L)
    check(part2(testInput) == 3121910778619)

    val input = readInput("Day${dayNr}")
    part1(input).println()
    part2(input).println()
}
