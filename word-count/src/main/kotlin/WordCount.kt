object WordCount {

    fun phrase(phrase: String): Map<String, Int> {
        val words = phrase.toLowerCase()
            .split(Regex("[^\\w']+"))
            .map { it.trim('\'', '\"') }
            .filter { it.isNotEmpty() }
        val counts = mutableMapOf<String, Int>()
        for (word in words) {
            val count = counts.getOrDefault(word, 0)
            counts[word] = count + 1
        }
        return counts
    }
}
