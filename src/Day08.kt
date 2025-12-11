import java.lang.Math.pow
import kotlin.math.pow

fun main() {
    val dayNr = "08"

    data class JunctionBox(val a: Int, val b: Int, val c: Int) {
        val connections: MutableSet<JunctionBox> = mutableSetOf()

        fun dist(other: JunctionBox): Int {
            return ((this.a - other.a).toDouble().pow(2.0)
                    + (this.b - other.b).toDouble().pow(2.0)
                    + (this.c - other.c).toDouble().pow(2.0)).toInt()
        }

        fun getNetwork(list: MutableSet<JunctionBox> = mutableSetOf()): Set<JunctionBox> {
            if (this in list) return list

            list.add(this)
            this.connections.forEach {
                it.getNetwork(list)
            }
            return list
        }

        override fun toString(): String {
            return "$a,$b,$c"
        }
    }

    fun boxesToDist(junctionBoxes: Set<JunctionBox>): MutableMap<Pair<JunctionBox, JunctionBox>, Int> {
        val map = mutableMapOf<Pair<JunctionBox, JunctionBox>, Int>()
        for (p in junctionBoxes) {
            for (q in junctionBoxes) {
                if (p == q) {
                    continue
                }

                if (Pair(p, q) in map) {
                    continue
                }
                if (Pair(q, p) in map) {
                    continue
                }

                val dist = p.dist(q)
                map[Pair(p, q)] = dist
            }
        }
        return map
    }

    fun part1(input: List<String>, connectionLimit: Int): Int {
        val junctionBoxes = mutableSetOf<JunctionBox>()
        input.forEach { line ->
            val (a, b, c) = line.split(",").map { it.toInt() }
            val junctionBox = JunctionBox(a, b, c)
            junctionBoxes.add(junctionBox)
        }

        val map = boxesToDist(junctionBoxes)
        val sortedMap = map.toList().sortedBy { it.second }.toMap()

        var k = 0
        for ((pair, dist) in sortedMap) {
            if (k == connectionLimit) {
                break
            }
            val (p, q) = pair
            if (p in q.connections || q in p.connections) {
                continue
            }
            p.connections.add(q)
            q.connections.add(p)
            k++
        }

        val visited = mutableSetOf<JunctionBox>()
        val networks = mutableListOf<Set<JunctionBox>>()
        junctionBoxes.forEach { v ->
            if (v !in visited) {
                val network = v.getNetwork()
                visited.addAll(network)
                networks.add(network)
            }
        }

        return networks.map { it.size }.sortedByDescending { it }.take(3).reduce { acc, i ->
            acc * i
        }
    }


    fun part2(input: List<String>): Int {
        val junctionBoxes = mutableSetOf<JunctionBox>()
        input.forEach { line ->
            val (a, b, c) = line.split(",").map { it.toInt() }
            val junctionBox = JunctionBox(a, b, c)
            junctionBoxes.add(junctionBox)
        }

        val map = boxesToDist(junctionBoxes)
        val sortedMap = map.toList().sortedBy { it.second }.toMap()

        for ((pair, dist) in sortedMap) {
            val (p, q) = pair
                if (p in q.connections || q in p.connections) {
                    continue
                }
            p.connections.add(q)
            q.connections.add(p)
            if (p.getNetwork().size == input.size) {
                return p.a * q.a
            }
        }

        val visited = mutableSetOf<JunctionBox>()
        val networks = mutableListOf<Set<JunctionBox>>()
        junctionBoxes.forEach { v ->
            if (v !in visited) {
                val network = v.getNetwork()
                visited.addAll(network)
                networks.add(network)
            }
        }

        return 1
    }

    val testInput = readInput("Day${dayNr}_test")
    check(part1(testInput, 10) == 40)
    part2(testInput).println()
    check(part2(testInput) == 25272)

    val input = readInput("Day${dayNr}")
    part1(input, 1000).println()
    part2(input).println()
}
