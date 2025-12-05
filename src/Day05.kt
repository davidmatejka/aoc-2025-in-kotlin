fun main() {
    val dayNr = "05"


    fun part1(input: List<String>): Int {
        val ranges = mutableListOf<LongRange>()
        val ids = mutableListOf<Long>()
        input.forEach { line ->
            if ("-" in line) {
                val (start, end) = line.split("-").map { it.toLong() }
                ranges.add(start..end)
            } else if (line.isNotBlank()) {
                ids.add(line.toLong())
            }
        }

        var sum = 0
        for (id in ids) {
            if (ranges.any { id in it }) {
                sum++
            }
        }

        return sum
    }

    fun insertRange(mergedRanges: MutableList<LongRange>, range: LongRange) {
        if (mergedRanges.isEmpty()) {
            mergedRanges.add(range)
            return
        }

        for (i in mergedRanges.indices) {
            val mergedRange = mergedRanges[i]

            // check if one range is fully embeddable in the other
            if (range.first >= mergedRange.first
                && range.last <= mergedRange.last
                ) {
                // range is already represented by a mergedRange -> return
                return
            }

            if ( range.first <= mergedRange.first
                && range.last >= mergedRange.last
            ) {
                // new incoming range is bigger than the one checking -> replace
                mergedRanges[i] = range
                return
            }

            // check partial overlap
            if (range.first in mergedRange) {
                /*
                 example:
                 mergedRange: 12-18
                 range: 16-20
                 16 in mergedRange -> append mergedRange with incoming range
               */
                val appendedRange = LongRange(mergedRange.first, range.last)
                mergedRanges[i] = appendedRange
                return
            }

            if (range.last in mergedRange) {
                /*
                 example:
                 mergedRange: 16-20
                 range: 12-18
                 18 in mergedRange -> prefix mergedRange with incoming range
               */
                val prefixRange = LongRange(range.first, mergedRange.last)
                mergedRanges[i] = prefixRange
                return
            }
        }

        // no overlap found -> add to mergedList
        mergedRanges.add(range)
    }

    fun checkAndMergeWithinRanges(mergedRanges: MutableList<LongRange>) {
        val removed = mergedRanges.removeFirst()
        insertRange(mergedRanges, removed)
    }


    fun part2(input: List<String>): Long {
        val ranges = mutableListOf<LongRange>()
        input.forEach { line ->
            if ("-" in line) {
                val (start, end) = line.split("-").map { it.toLong() }
                ranges.add(start..end)
            }
        }

        val mergedRanges = mutableListOf<LongRange>()
        ranges.forEach { range ->
            insertRange(mergedRanges, range)
        }

        repeat(mergedRanges.indices.count()) { // check if now ranges within the list overlap with each other
            checkAndMergeWithinRanges(mergedRanges)
        }

        val mapped = mergedRanges.map { it.last - it.first + 1 } // +1 to count last one as well
        return mapped.sum()
    }

    val testInput = readInput("Day${dayNr}_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)
    part2(testInput)

    val input = readInput("Day${dayNr}")
    part1(input).println()
    part2(input).println()
}
