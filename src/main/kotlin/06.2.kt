fun main() {
	val input = readLineFromFile("06.txt")
	val result = getFirstUniqueSubstringOfLength(input, 14)
	println(result)
}

private fun getFirstUniqueSubstringOfLength(string: String, length: Int = 4): Int {
	for (i in length..string.length) {
		val substring = string.substring(i - length, i)
		if (length == substring.toSet().size) {
			return i
		}
	}
	return -1
}
