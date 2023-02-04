import java.util.stream.Stream

fun main() {
	val lines: Stream<String> = readLinesFromFile("02.txt")
	val input: Stream<Pair<RockPaperScissors, RockPaperScissors>> = prepareInput(lines)
	val result: Int = calculateScore(input)
	println(result)
}

enum class RockPaperScissors(val score: Int) {
	ROCK(1),
	PAPER(2),
	SCISSORS(3),
	UNKNOWN(0),
}


enum class RockPaperScissorsOutcome(val score: Int) {
	WIN(6),
	DRAW(3),
	LOST(0),
}

private fun transformInput(value: String): RockPaperScissors {
	return when (value) {
		"A", "X" -> RockPaperScissors.ROCK
		"B", "Y" -> RockPaperScissors.PAPER
		"C", "Z" -> RockPaperScissors.SCISSORS
		else -> RockPaperScissors.UNKNOWN
	}
}

private fun prepareInput(lines: Stream<String>): Stream<Pair<RockPaperScissors, RockPaperScissors>> {
	return lines.map() {
		val values = it.split(" ", limit = 2)
		return@map Pair(transformInput(values[0]), transformInput(values[1]))
	}
}

private fun calculateScore(strategy: Stream<Pair<RockPaperScissors, RockPaperScissors>>): Int {
	return strategy.map { (first, second) ->
		val outcome = determineOutcome(first, second)
		return@map second.score + outcome.score
	}.reduce(Integer::sum).orElse(0)
}


private val possibleOutcomes: Map<Pair<RockPaperScissors, RockPaperScissors>, RockPaperScissorsOutcome> = mapOf(
	Pair(RockPaperScissors.ROCK, RockPaperScissors.ROCK) to RockPaperScissorsOutcome.DRAW,
	Pair(RockPaperScissors.ROCK, RockPaperScissors.PAPER) to RockPaperScissorsOutcome.WIN,
	Pair(RockPaperScissors.ROCK, RockPaperScissors.SCISSORS) to RockPaperScissorsOutcome.LOST,
	Pair(RockPaperScissors.PAPER, RockPaperScissors.ROCK) to RockPaperScissorsOutcome.LOST,
	Pair(RockPaperScissors.PAPER, RockPaperScissors.PAPER) to RockPaperScissorsOutcome.DRAW,
	Pair(RockPaperScissors.PAPER, RockPaperScissors.SCISSORS) to RockPaperScissorsOutcome.WIN,
	Pair(RockPaperScissors.SCISSORS, RockPaperScissors.ROCK) to RockPaperScissorsOutcome.WIN,
	Pair(RockPaperScissors.SCISSORS, RockPaperScissors.PAPER) to RockPaperScissorsOutcome.LOST,
	Pair(RockPaperScissors.SCISSORS, RockPaperScissors.SCISSORS) to RockPaperScissorsOutcome.DRAW,
)

private fun determineOutcome(opponentStrategy: RockPaperScissors, yoursStrategy: RockPaperScissors): RockPaperScissorsOutcome {
	return possibleOutcomes[Pair(opponentStrategy, yoursStrategy)] ?: RockPaperScissorsOutcome.DRAW
}