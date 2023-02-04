import org.apache.commons.lang3.StringUtils
import java.io.BufferedReader
import java.util.stream.Stream

fun readLinesFromFile(file: String): Stream<String> {
	val reader: BufferedReader? = object {}.javaClass.getResourceAsStream(file)?.bufferedReader()
	return reader?.lines() ?: Stream.empty()
}

fun readLineFromFile(file: String): String {
	return readLinesFromFile(file).findFirst().orElse(StringUtils.EMPTY)
}

fun transposeMatrix(matrix: List<List<Int>>): List<List<Int>> {
	val transposition = Array(matrix[0].size) { IntArray(matrix.size) }

	for (y in matrix.indices) {
		for (x in 0 until matrix[0].size) {
			transposition[x][y] = matrix[y][x]
		}
	}

	return transposition.toList().map { it.toList() }
}