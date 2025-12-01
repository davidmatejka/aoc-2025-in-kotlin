fun main() {
    val dayNr = "01"
    fun part1(input: List<String>): Int {
        var dialPosition = 50
        var sumLeftAtZero = 0

        for (rotation in input) {
            val dir = rotation.first()
            val steps = rotation.drop(1).toInt()

            when (dir) {
                'R' -> dialPosition += steps
                'L' -> dialPosition -= steps
            }
            dialPosition = dialPosition.mod(100)
            if (dialPosition == 0) sumLeftAtZero++
        }
        return sumLeftAtZero
    }

    fun part2(input: List<String>): Int {
        var dialPosition = 50
        var sumPointedAtZero = 0

        for (rotation in input) {
            val dir = rotation.first()
            val steps = rotation.drop(1).toInt()

            when (dir) {
                'R' -> {
                    (1..steps).forEach { _ ->
                        dialPosition++
                        dialPosition = dialPosition.mod(100)
                        if (dialPosition == 0) {
                            sumPointedAtZero++
                        }
                    }
                }

                'L' -> {
                    (1..steps).forEach { _ ->
                        dialPosition--
                        dialPosition = dialPosition.mod(100)
                        if (dialPosition == 0) {
                            sumPointedAtZero++
                        }
                    }
                }
            }
        }
        return sumPointedAtZero
    }

    val testInput = readInput("Day${dayNr}_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    val input = readInput("Day${dayNr}")
    part1(input).println()
    part2(input).println()
}
