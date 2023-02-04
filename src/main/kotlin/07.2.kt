import java.util.stream.Stream


fun main() {
	val lines: Stream<String> = readLinesFromFile("07.txt")
	val rootDirectory = Directory()
	rootDirectory.parseCommands(lines)

	val filesystemSize = 70000000
	val required = 30000000


	val currentSize = rootDirectory.getSize()
	val available = filesystemSize - currentSize

	val result = rootDirectory.getNestedDirectoriesSizes()
		.stream()
		.filter { available + it >= required }
		.reduce(Integer::min)
		.orElse(currentSize)

	println(result)
}
