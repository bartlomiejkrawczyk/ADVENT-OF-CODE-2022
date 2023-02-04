import java.util.stream.Stream

fun main() {
	val lines: Stream<String> = readLinesFromFile("08.txt")
	val trees: Forest = parseInput(lines)
	val result = calculateVisibility(trees)
	println(result)
}

private fun parseInput(lines: Stream<String>): List<List<Int>> {
	return lines.map {
		it.toCharArray().map { digit -> digit.digitToInt() }
	}.toList()
}

private fun calculateVisibility(forest: Forest): Int {
	var maxValue = 0
	val forestX = transposeMatrix(forest)
	for (y in forest.indices) {
		for (x in 0 until forest[0].size) {
			maxValue = maxOf(maxValue, calculateVisibilityScore(forestX, forest, x, y))
		}
	}
	return maxValue
}

private fun calculateVisibilityScore(forestX: Forest, forestY: Forest, treeX: Int, treeY: Int): Int {
	val treeSize = forestY[treeY][treeX]

	var visibility = 1
	for (direction in treeLines(forestX, forestY, treeX, treeY)) {
		visibility *= calculateVisibleTreeInLine(direction, treeSize)
	}

	return visibility
}

private fun calculateVisibleTreeInLine(treeLine: List<Int>, treeSize: Int): Int {
	var current = 0
	for (value in treeLine) {
		current++
		if (value >= treeSize) {
			break
		}
	}
	return current
}

private fun treeLines(forestX: Forest, forestY: Forest, treeX: Int, treeY: Int): List<List<Int>> {
	return listOf(
		forestY[treeY].subList(0, treeX).reversed(),
		forestY[treeY].subList(treeX + 1, forestX.size),
		forestX[treeX].subList(0, treeY).reversed(),
		forestX[treeX].subList(treeY + 1, forestX.size),
	)
}
