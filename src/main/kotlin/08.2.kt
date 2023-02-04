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

private fun calculateVisibilityScore(forestX: Forest, forestY: Forest, treeX: Int, treeY: Int): Int {
	val treeSize = forestY[treeY][treeX]

	var visibility = 1
	visibility *= calculateVisibleTreeInLine(forestY[treeY].subList(0, treeX).reversed(), treeSize)
	visibility *= calculateVisibleTreeInLine(forestY[treeY].subList(treeX + 1, forestX.size), treeSize)
	visibility *= calculateVisibleTreeInLine(forestX[treeX].subList(0, treeY).reversed(), treeSize)
	visibility *= calculateVisibleTreeInLine(forestX[treeX].subList(treeY + 1, forestY.size), treeSize)

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

private fun transposeMatrix(matrix: List<List<Int>>): List<List<Int>> {
	val transposition = Array(matrix[0].size) { IntArray(matrix.size) }

	for (y in matrix.indices) {
		for (x in 0 until matrix[0].size) {
			transposition[x][y] = matrix[y][x]
		}
	}

	return transposition.toList().map { it.toList() }
}

