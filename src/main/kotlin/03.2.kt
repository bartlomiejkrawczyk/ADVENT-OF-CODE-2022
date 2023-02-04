import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.stream.Stream

fun main() {
	val lines: Stream<String> = readLinesFromFile("03.txt")
	val input: Flux<List<Set<Char>>> = parseInput(lines)
	val result: Mono<Int> = calculateTotalPriorities(input)
	println(result.blockOptional().orElse(0))
}


private fun parseInput(lines: Stream<String>): Flux<List<Set<Char>>> {
	return Flux.fromStream(lines)
		.map { it.toSet() }
		.buffer(3)
}

private fun calculateTotalPriorities(rucksacks: Flux<List<Set<Char>>>): Mono<Int> {
	return rucksacks.map { calculatePriorities(it) }.reduce(Integer::sum)
}

private fun calculatePriorities(rucksacks: List<Set<Char>>): Int {
	return rucksacks.stream()
		.reduce { a, b -> a.intersect(b) }
		.map { it.stream() }
		.orElse(Stream.empty())
		.map {
			if (it.isUpperCase()) it.code - 'A'.code + 27 else it.code - 'a'.code + 1
		}
		.reduce(Integer::sum)
		.orElse(0)
}