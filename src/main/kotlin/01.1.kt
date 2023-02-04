import java.util.stream.Stream

fun main() {
	val lines: Stream<String> = readLinesFromFile("01.txt")
	val result: Int = calculateMaxCalories(lines)
	println(result)
}


private fun calculateMaxCalories(foodCalories: Stream<String>): Int {
	var currentCalories = 0
	var maxCalories = 0

	for (line in foodCalories) {
		if (line.isBlank()) {
			currentCalories = 0
		} else {
			currentCalories += Integer.valueOf(line)
		}

		maxCalories = maxOf(maxCalories, currentCalories)
	}
	return maxCalories
}