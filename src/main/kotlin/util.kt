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