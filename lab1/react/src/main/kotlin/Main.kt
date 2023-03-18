fun main() {
    val reactor = Reactor<Int>()
    val input = reactor.InputCell(1)
    val plusOne = reactor.ComputeCell(input) { it[0] + 1 }
    val minusOne1 = reactor.ComputeCell(input) { it[0] - 1 }
    val minusOne2 = reactor.ComputeCell(minusOne1) { it[0] - 1 }
    val output = reactor.ComputeCell(plusOne, minusOne2) { (x, y) -> x * y }

    val vals = mutableListOf<Int>()
    output.addCallback { vals.add(it) }

    input.value = 4
    //assertEquals(listOf(10), vals)
    println("VALS = $vals")
}