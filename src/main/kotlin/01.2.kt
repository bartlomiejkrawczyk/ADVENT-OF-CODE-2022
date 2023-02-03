import java.io.BufferedReader
import java.util.stream.Stream

fun main() {
	val reader: BufferedReader? = object {}.javaClass.getResourceAsStream("01.txt")?.bufferedReader()

	reader?.let {
		val result: Int = calculateMaxCaloriesCarriedByTopElves(it.lines())
		println(result)
	}
}


private fun calculateMaxCaloriesCarriedByTopElves(foodCalories: Stream<String>, number: Int = 3): Int {
	val caloriesPerElf: MutableList<Int> = ArrayList()

	var currentCalories = 0

	for (line in foodCalories) {
		if (line.isBlank()) {
			caloriesPerElf.add(currentCalories)
			currentCalories = 0
		} else {
			currentCalories += Integer.valueOf(line)
		}
	}

	val maxCalories = caloriesPerElf.sortedDescending().subList(0, number)

	return maxCalories.sum()
}