fun main() {
    val dayNr = "11"

    fun bfs(devices: Map<String, Set<String>>, start: String, goal: String): Int {
        var sum = 0

        val q = mutableListOf(start)

        while (q.isNotEmpty()) {
            val current = q.removeFirst()

            if (current == goal) {
                sum++
                continue
            }

            for (neighbour in devices[current] ?: emptyList()) {
                q.add(neighbour)
            }
        }

        return sum
    }

    fun part1(input: List<String>): Int {
        val devices = input.fold(mutableMapOf<String, Set<String>>()) { acc, line ->
            val (name, rest) = line.split(": ")
            val neighbours = rest.split(" ")
            acc[name] = neighbours.toSet()
            acc
        }

        return bfs(devices, "you", "out")
    }

    fun dfs(
        map: Map<String, Set<String>>,
        start: String,
        goal: String,
        cache: MutableMap<String, Long> = mutableMapOf()
    ): Long {
        if (start == goal) {
            return 1L
        }

        return cache.getOrPut(start) {
            var sum = 0L
            for (next in map[start] ?: emptyList()) {
                sum += dfs(map, next, goal, cache)
            }
            sum
        }
    }

    fun part2(input: List<String>): Long {
        val map = input.fold(mutableMapOf<String, Set<String>>()) { acc, line ->
            val (name, rest) = line.split(": ")
            val neighbours = rest.split(" ")
            acc[name] = neighbours.toSet()
            acc
        }

        return (dfs(map, "svr", "dac") * dfs(map, "dac", "fft") * dfs(map, "fft", "out")
        + dfs(map, "svr", "fft") * dfs(map, "fft", "dac") * dfs(map, "dac", "out"))
    }


    val testInput = readInput("Day${dayNr}_test")
    val testInput2 = readInput("Day${dayNr}_test2")
    check(part1(testInput) == 5)
    check(part2(testInput2) == 2L)

    val input = readInput("Day${dayNr}")
    part1(input).println()
    part2(input).println()
}