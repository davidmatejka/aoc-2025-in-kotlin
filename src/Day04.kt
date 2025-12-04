fun main() {
    val dayNr = "04"

    fun parseInput(input: List<String>): MutableList<MutableList<Char>> {
        val map = mutableListOf<MutableList<Char>>()
        input.forEach { line ->
            map.add(line.toMutableList())
        }
        return map
    }

    fun countAccessiblePositions(map: List<List<Char>>): Pair<Int, List<MutableList<Char>>> {
        var sum = 0
        val copy = mutableListOf<MutableList<Char>>()
        map.forEach { line ->
            copy.add(line.toMutableList())
        }
        for (y in map.indices) {
            for (x in map[0].indices) {
                if (map[y][x] == '.') {
                    continue
                }
                var adjacentCountOfPaper = 0
                for (dy in -1..1) {
                    for (dx in -1..1) {
                        val ny = y + dy
                        val nx = x + dx
                        if (nx == x && ny == y) {
                            continue
                        }
                        if ( ny in map.indices
                            && nx in map[0].indices
                            && map[ny][nx] == '@'
                            ) {
                            adjacentCountOfPaper++
                        }
                    }
                }
                if (adjacentCountOfPaper < 4) {
                    copy[y][x] = '.'
                    sum++
                }
            }
        }
        return Pair(sum, copy)
    }

    fun part1(input: List<String>): Int {
        val map = parseInput(input)
        return countAccessiblePositions(map).first
    }

    fun part2(input: List<String>): Int {
        var map: List<MutableList<Char>> = parseInput(input)
        var sum = 0
        var pair = countAccessiblePositions(map)
        var removedPaperRolls = pair.first
        map = pair.second
        sum += removedPaperRolls

        while (removedPaperRolls > 0) {
            pair = countAccessiblePositions(map)
            removedPaperRolls = pair.first
            map = pair.second
            sum += removedPaperRolls
        }

        return sum
    }

    val testInput = readInput("Day${dayNr}_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    val input = readInput("Day${dayNr}")
    part1(input).println()
    part2(input).println()
}
