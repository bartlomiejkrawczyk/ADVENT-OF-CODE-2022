import java.io.BufferedReader
import java.util.stream.Stream

fun main() {
	val reader: BufferedReader? = object {}.javaClass.getResourceAsStream("01.txt")?.bufferedReader()

	reader?.let {
		val result: Int = calculateMaxCalories(it.lines())
		println(result)
	}
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