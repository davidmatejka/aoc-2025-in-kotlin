import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.path
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.max

fun main() {
    val dayNr = "09"

    data class Coord(val x: Long, val y: Long) {
        fun distance (other: Coord): Double {
            return sqrt((this.x - other.x).toDouble().pow( 2.0)
                    + (this.y - other.y).toDouble().pow(2.0)
            )
        }
    }

    fun calcArea(c1: Coord, c2: Coord): Long {
        val a = abs(c2.x-c1.x) + 1
        val b = abs(c2.y-c1.y) + 1
        return a * b
    }

    fun getMaxArea(corners: List<Coord>): Long {
        var maxArea = 0L
        for (c1 in corners) {
            loop@for (c2 in corners) {
                // check if another corner exists inside the area
                // then the area isn't fully inside
                for (c3 in corners) {
                    val leftC = if (c1.x < c2.x) c1 else c2
                    val rightC = if (c1.x > c2.x) c1 else c2
                    val lowerC = if (c1.y < c2.y) c1 else c2
                    val higherC = if (c1.y > c2.y) c1 else c2

                    if (c3.x < rightC.x && c3.x > leftC.x
                        && c3.y > lowerC.y && c3.y < higherC.y) {
                        continue@loop
                    }
                }
                val area = calcArea(c1, c2)
                if (area > maxArea) maxArea = area
            }
        }

        return maxArea
    }

    fun part1(input: List<String>): Long {
        val corners = input.fold(mutableListOf<Coord>()) { acc, line ->
            val (x, y) = line.split(",").map { it.toLong() }
            acc.add(Coord(x,y))
            acc
        }

        var maxArea = 0L
        for (c1 in corners) {
            for (c2 in corners) {
                val area = calcArea(c1, c2)
                if (area > maxArea) maxArea = area
            }
        }

        return maxArea
    }

    fun draw(input: List<String>) {
        val corners = input.fold(mutableListOf<Coord>()) { acc, line ->
            val (x, y) = line.split(",").map { it.toLong() }
            acc.add(Coord(x, y))
            acc
        }
        val xs = mutableListOf<Long>()
        val ys = mutableListOf<Long>()

        corners.forEach {
            xs.add(it.x)
            ys.add(it.y)
        }
        xs.add(corners.first().x)
        ys.add(corners.first().y)

        plot {
            layout {
                size = 1000 to 1000
            }
            path {
                x(xs)
                y(ys)
            }
        }.save("Day09.png")
    }

    fun part2(input: List<String>): Long {
        val corners = input.fold(mutableListOf<Coord>()) { acc, line ->
            val (x, y) = line.split(",").map { it.toLong() }
            acc.add(Coord(x, y))
            acc
        }

        // draw(input) draw the input to see the special figure

        val pairs = mutableListOf<Pair<Coord,Coord>>()
        for (i in input.indices) {
            val (a, b) = input[i].split(",").map { it.toLong() }

            var k = i+1
            if (k !in input.indices) { k = 0 }
            val (c, d) = input[k].split(",").map { it.toLong() }

            pairs.add(Pair(Coord(a,b), Coord(c,d)))
        }
        pairs.sortWith ( compareByDescending { (p,q) -> p.distance(q) } )
        val (lowerY, upperY) = pairs.filter { (p,q) -> p.y == q.y }.take(2).map { it.first.y }

        return max(getMaxArea(corners.filter { it.y >= upperY }),
        getMaxArea(corners.filter { it.y <= lowerY }))
    }

    val testInput = readInput("Day${dayNr}_test")
    check(part1(testInput) == 50L)

    val input = readInput("Day${dayNr}")
    part1(input).println()
    part2(input).println()
}
