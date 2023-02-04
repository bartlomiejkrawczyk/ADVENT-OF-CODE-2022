import java.io.BufferedReader
import java.util.stream.Stream

fun main() {
	val reader: BufferedReader? = object {}.javaClass.getResourceAsStream("03.txt")?.bufferedReader()

	reader?.let {
		val input: Stream<Pair<Set<Char>, Set<Char>>> = parseInput(reader.lines())
		val result: Int = calculateTotalPriorities(input)
		println(result)
	}
}


private fun parseInput(lines: Stream<String>): Stream<Pair<Set<Char>, Set<Char>>> {
	return lines.map {
		val divisionPoint = it.length / 2
		return@map Pair(it.substring(0, divisionPoint), it.substring(divisionPoint))
	}.map { (first, second) ->
		return@map Pair(first.toSet(), second.toSet())
	}
}

private fun calculateTotalPriorities(rucksacks: Stream<Pair<Set<Char>, Set<Char>>>): Int {
	return rucksacks.map { (first, second) -> calculatePriorities(first, second) }.reduce(Integer::sum).orElse(0)
}

private fun calculatePriorities(first: Set<Char>, second: Set<Char>): Int {
	val intersection = first.intersect(second)
	return intersection.stream()
		.map {
			if (it.isUpperCase()) it.code - 'A'.code + 27 else it.code - 'a'.code + 1
		}
		.reduce(Integer::sum)
		.orElse(0)
}