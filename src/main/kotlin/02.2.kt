import java.io.BufferedReader
import java.util.stream.Stream

fun main() {
	val reader: BufferedReader? = object {}.javaClass.getResourceAsStream("02.txt")?.bufferedReader()

	reader?.let {
		val input: Stream<Pair<RockPaperScissors, RockPaperScissorsOutcome>> = prepareInput(it.lines())
		val result: Int = calculateScore(input)
		println(result)
	}
}

private fun transformOpponentsInput(value: String): RockPaperScissors {
	return when (value) {
		"A" -> RockPaperScissors.ROCK
		"B" -> RockPaperScissors.PAPER
		"C" -> RockPaperScissors.SCISSORS
		else -> RockPaperScissors.UNKNOWN
	}
}

private fun transformOutcomeInput(value: String): RockPaperScissorsOutcome {
	return when (value) {
		"X" -> RockPaperScissorsOutcome.LOST
		"Y" -> RockPaperScissorsOutcome.DRAW
		"Z" -> RockPaperScissorsOutcome.WIN
		else -> RockPaperScissorsOutcome.LOST
	}
}

private fun prepareInput(lines: Stream<String>): Stream<Pair<RockPaperScissors, RockPaperScissorsOutcome>> {
	return lines.map() {
		val values = it.split(" ", limit = 2)
		return@map Pair(transformOpponentsInput(values[0]), transformOutcomeInput(values[1]))
	}
}

private val yourPlay: Map<Pair<RockPaperScissors, RockPaperScissorsOutcome>, RockPaperScissors> = mapOf(
	Pair(RockPaperScissors.ROCK, RockPaperScissorsOutcome.LOST) to RockPaperScissors.SCISSORS,
	Pair(RockPaperScissors.ROCK, RockPaperScissorsOutcome.DRAW) to RockPaperScissors.ROCK,
	Pair(RockPaperScissors.ROCK, RockPaperScissorsOutcome.WIN) to RockPaperScissors.PAPER,
	Pair(RockPaperScissors.PAPER, RockPaperScissorsOutcome.LOST) to RockPaperScissors.ROCK,
	Pair(RockPaperScissors.PAPER, RockPaperScissorsOutcome.DRAW) to RockPaperScissors.PAPER,
	Pair(RockPaperScissors.PAPER, RockPaperScissorsOutcome.WIN) to RockPaperScissors.SCISSORS,
	Pair(RockPaperScissors.SCISSORS, RockPaperScissorsOutcome.LOST) to RockPaperScissors.PAPER,
	Pair(RockPaperScissors.SCISSORS, RockPaperScissorsOutcome.DRAW) to RockPaperScissors.SCISSORS,
	Pair(RockPaperScissors.SCISSORS, RockPaperScissorsOutcome.WIN) to RockPaperScissors.ROCK,
)

private fun calculateScore(strategy: Stream<Pair<RockPaperScissors, RockPaperScissorsOutcome>>): Int {
	return strategy.map { (opponentsPlay, outcome) ->
		return@map outcome.score + (yourPlay[Pair(opponentsPlay, outcome)]?.score ?: 0)
	}.reduce(Integer::sum).orElse(0)
}