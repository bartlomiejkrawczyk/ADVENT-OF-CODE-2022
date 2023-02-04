import java.util.stream.Stream
import kotlin.jvm.optionals.getOrNull

fun main() {
	val lines: Stream<String> = readLinesFromFile("07.txt")

	val rootDirectory = Directory()

	rootDirectory.parseCommands(lines)

	val result = rootDirectory.getNestedDirectoriesSizes()
		.stream()
		.filter { it <= 100000 }
		.reduce(Integer::sum)
		.orElse(0)

	println(result)
}

class Directory {
	private val directories: MutableMap<String, Directory> = HashMap()
	private val files: MutableMap<String, Int> = HashMap()
	private var parent: Directory? = null

	fun getSize(): Int {
		val directoriesSize = directories.values.stream().map { it.getSize() }.reduce(Integer::sum).orElse(0)
		val filesSize = files.values.stream().reduce(Integer::sum).orElse(0)
		return directoriesSize + filesSize
	}

	fun getNestedDirectoriesSizes(): MutableList<Int> {
		val directoriesSize: List<Int>? = directories.values.stream()
			.map { it.getNestedDirectoriesSizes() }
			.reduce { a, b ->
				a.addAll(b)
				return@reduce a
			}
			.getOrNull()

		val result = ArrayList<Int>()

		result.add(getSize())

		directoriesSize?.let {
			result.addAll(it)
		}

		return result
	}

	private fun findDirectory(name: String): Directory? {
		return directories[name]
	}

	private fun addDirectory(name: String, newDirectory: Directory = Directory()) {
		newDirectory.parent = this
		directories[name] = newDirectory
	}

	private fun addFile(name: String, size: Int) {
		files[name] = size
	}

	override fun toString(): String {
		return "Directory(directories=$directories, files=$files)"
	}

	fun parseCommands(lines: Stream<String>) {
		var workingDirectory = this

		for (line in lines) {
			when {
				line.startsWith("\$ cd ..") -> workingDirectory = workingDirectory.parent ?: this
				line.startsWith("\$ cd") -> {
					val directoryName = line.substring(5)
					workingDirectory = workingDirectory.findDirectory(directoryName) ?: workingDirectory
				}

				line.startsWith("\$") -> continue
				line.startsWith("dir ") -> {
					val directoryName = line.substring(4)
					workingDirectory.addDirectory(directoryName)
				}

				else -> {
					val values = line.split(" ", limit = 2)
					val fileSize = Integer.valueOf(values[0])
					val fileName = values[1]
					workingDirectory.addFile(fileName, fileSize)
				}

			}
		}
	}
}
