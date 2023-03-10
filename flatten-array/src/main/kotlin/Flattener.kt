object Flattener {
    fun flatten(source: Collection<Any?>): List<Any> {
        val result = mutableListOf<Any>()
        source.forEach {
            when (it) {
                is Collection<Any?> -> result.addAll(flatten(it))
                null -> {}
                else -> result.add(it)
            }
        }
        return result
    }
}
