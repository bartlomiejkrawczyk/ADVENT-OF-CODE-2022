import java.io.BufferedReader
import java.util.stream.Stream

fun readLinesFromFile(file: String): Stream<String> {
	val reader: BufferedReader? = object {}.javaClass.getResourceAsStream(file)?.bufferedReader()
	return reader?.lines() ?: Stream.empty()
}