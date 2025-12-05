fun main() {
    val dayNr = "06"

    data class Problem(val operator: String, val numbers: List<String>)

    fun parseInput(input: List<String>): List<Problem> {
        val lines = mutableListOf<List<String>>()
        input.forEach {
            lines.add(it.trimIndent().split(regex = Regex("""\s+""")))
        }

        val problems = mutableListOf<Problem>()
        for (i in lines[0].indices) {
            val operator = lines[lines.lastIndex][i]
            val numbers = mutableListOf<String>()
            for (j in 0..lines.size - 2) {
                numbers.add(lines[j][i])
            }
            problems.add(Problem(operator, numbers))
        }
        return problems
    }

    fun part1(input: List<String>): Long {
        val problems = parseInput(input)
        var sum = 0L
        problems.forEach { problem ->
            if (problem.operator == "*") {
                sum += problem.numbers.map { it.toLong() }.reduce {acc, n ->
                    acc * n
                }
            } else {
                sum += problem.numbers.sumOf{it.toLong()}
            }
        }

        return sum
    }


    fun part2(input: List<String>): Long {
        val lines = mutableListOf<MutableList<Char>>()
        input.forEach {
            lines.add(it.toMutableList())
        }
        val maxSize = lines.maxBy { it.size }.size
        lines.forEach { line ->
            val diff = maxSize - line.size
            repeat(diff) { line.add(' ') }
        }

        var operator = ' '
        var sum = 0L
        var current = 0L
        for (i in lines.last().indices) {
            if (lines.last()[i] != ' ') {
                operator = lines.last()[i]
                if (operator == '*') {
                    sum += current
                    current = 1
                }
                if (operator == '+') {
                    sum += current
                    current = 0
                }
            }

            val num = buildString {
                for (j in lines.indices) {
                    val digit = lines[j][i]
                    if (digit.isDigit()) {
                        append(digit)
                    }
                }
            }
            if (num == "") continue
            if (operator == '+') { current += num.toLong() }
            if (operator == '*') { current *= num.toLong() }
        }
        sum += current
        return sum
    }

    val testInput = readInput("Day${dayNr}_test")
    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)

    val input = readInput("Day${dayNr}")
    part1(input).println()
    part2(input).println()
}
