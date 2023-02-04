import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.stream.Stream

fun main() {
	val lines: Stream<String> = readLinesFromFile("04.txt")
	val input: Flux<Pair<Range, Range>> = parseInput(lines)
	val result: Mono<Int> = sumFullyContained(input)
	println(result.blockOptional().orElse(0))

}

typealias Range = Pair<Int, Int>

private fun parseInput(lines: Stream<String>): Flux<Pair<Range, Range>> {
	return Flux.fromStream(lines)
		.map {
			val elves = it.split(",")
			return@map Pair(parseRange(elves[0]), parseRange(elves[1]))
		}
}

private fun parseRange(range: String): Range {
	val splitted = range.split("-")
	return Pair(Integer.valueOf(splitted[0]), Integer.valueOf(splitted[1]))
}

private fun Range.contains(that: Range): Boolean {
	return this.first <= that.first && that.second <= this.second
}

private fun sumFullyContained(rangePairs: Flux<Pair<Range, Range>>): Mono<Int> {
	return rangePairs
		.filter { (first, second) ->
			return@filter first.contains(second) || second.contains(first)
		}
		.map { 1 }
		.reduce(Integer::sum)
}
