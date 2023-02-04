import java.util.stream.Stream

fun main() {
	val lines: Stream<String> = readLinesFromFile("08.txt")
	val trees: Forest = parseInput(lines)
	val result = calculateVisible(trees)
	println(result)
}

typealias Forest = List<List<Int>>

private fun parseInput(lines: Stream<String>): List<List<Int>> {
	return lines.map {
		it.toCharArray().map { digit -> digit.digitToInt() }
	}.toList()
}

private fun isNotVisible(forestX: Forest, forestY: Forest, treeX: Int, treeY: Int): Boolean {
	if (treeX <= 0 || treeY <= 0 || treeX >= forestX.size - 1 || treeY >= forestY.size - 1) {
		return false
	}

	val treeSize = forestY[treeY][treeX]

	if (treeSize > forestY[treeY].subList(0, treeX).max() || treeSize > forestY[treeY].subList(treeX + 1, forestX.size).max()) {
		return false
	}

	if (treeSize > forestX[treeX].subList(0, treeY).max() || treeSize > forestX[treeX].subList(treeY + 1, forestX.size).max()) {
		return false
	}

	return true
}

private fun calculateVisible(forest: Forest): Int {
	var count = 0
	val forestY = transposeMatrix(forest)
	for (y in forest.indices) {
		for (x in 0 until forest[0].size) {
			if (!isNotVisible(forest, forestY, x, y)) {
				count += 1
			}
		}
	}
	return count
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

