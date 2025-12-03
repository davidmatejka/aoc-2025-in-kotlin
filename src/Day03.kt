fun main() {
    val dayNr = "03"

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach { bank ->
            var tmp = bank.toList().dropLast(1).map { it.digitToInt() }
            var currentMax = tmp.first()
            var currentMaxIndex = 0
            for (i in tmp.indices) {
                if (tmp[i] > currentMax) {
                    currentMax = tmp[i]
                    currentMaxIndex = i
                }
            }

            tmp = bank.map { it.digitToInt() }.toMutableList()
            tmp.removeAt(currentMaxIndex)
            var currentMaxSecondBattery = Int.MIN_VALUE
            for (i in currentMaxIndex..<tmp.size) {
                if (tmp[i] > currentMaxSecondBattery) {
                    currentMaxSecondBattery = tmp[i]
                }
            }
            sum += ("$currentMax$currentMaxSecondBattery").toInt()
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        var sum = 0L
        input.forEach { bank ->
            var copy = bank.toMutableList().map { it.digitToInt() }
            val erg = mutableListOf<Int>()
            for (i in 11 downTo 0) {
                val head = copy.dropLast(i)
                val max = head.max()
                val maxIndex = head.indexOf(max)
                erg.add(max)
                copy = copy.drop(maxIndex + 1)
            }
            sum += erg.joinToString("").toLong()
        }
        return sum
    }

    val testInput = readInput("Day${dayNr}_test")
    check(part1(testInput) == 357)
    check(part2(testInput) == 3121910778619)

    val input = readInput("Day${dayNr}")
    part1(input).println()
    part2(input).println()
}
