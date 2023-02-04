import io.vavr.Tuple
import java.util.Stack
import java.util.stream.Stream
import kotlin.collections.ArrayList

fun main() {
	val lines: Stream<String> = readLinesFromFile("05.txt")
	val input = parseInput(lines)
	performInstructions(input.first, input.second)
	for (stack in input.first) {
		print(stack.peek())
	}
}

private fun parseInput(lines: Stream<String>): Pair<List<Stack<Char>>, List<Instruction>> {
	var containers = true
	val stacks: MutableList<Stack<Char>> = ArrayList()
	val instructions: MutableList<Instruction> = ArrayList()

	for (line in lines) {
		if (line.isBlank()) {
			containers = false
		} else if (containers) {
			val stack = Stack<Char>()
			for (letter in line) {
				stack.push(letter)
			}
			stacks.add(stack)
		} else {
			val splitted = line.split(" ")
			val instruction = Tuple.of(
				Integer.valueOf(splitted[1]),
				Integer.valueOf(splitted[3]),
				Integer.valueOf(splitted[5]),
			)
			instructions.add(instruction)
		}
	}

	return Pair(stacks, instructions)
}

private fun performInstructions(stacks: List<Stack<Char>>, instructions: List<Instruction>) {
	for (instruction in instructions) {
		val fromStack = stacks[instruction._2 - 1]
		val toStack = stacks[instruction._3 - 1]
		val count = instruction._1

		val temporaryStack: Stack<Char> = Stack()

		for (i in 0 until count) {
			temporaryStack.push(fromStack.pop())
		}

		for (i in 0 until count) {
			toStack.push(temporaryStack.pop())
		}
	}
}
