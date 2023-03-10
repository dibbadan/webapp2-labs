class Matrix(private val matrixAsString: String) {

    private val rows = matrixAsString.trim().split("\n").map { it.trim().split(" ").map { it.toInt() } }
    fun column(colNr: Int): List<Int> {
        return rows.map { it[colNr - 1] }
    }

    fun row(rowNr: Int): List<Int> {
        return rows[rowNr - 1]
    }
}
