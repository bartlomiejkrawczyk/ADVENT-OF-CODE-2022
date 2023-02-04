import java.util.stream.Stream

fun main() {
	val lines: Stream<String> = readLinesFromFile("01.txt")
	val result: Int = calculateMaxCaloriesCarriedByTopElves(lines)
	println(result)
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