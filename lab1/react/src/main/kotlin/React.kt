import kotlin.properties.Delegates

class Reactor<T>() {

    // Your compute cell's addCallback method must return an object
    // that implements the Subscription interface.
    interface Subscription {
        fun cancel()
    }

    abstract inner class Cell<T> {
        abstract val value: T
        val dependentComputeCells = mutableSetOf<ComputeCell>()

        fun addDependentComputeCell(computeCell: ComputeCell) {
            dependentComputeCells.add(computeCell)
        }

        fun removeDependentComputeCell(computeCell: ComputeCell) {
            dependentComputeCells.remove(computeCell)
        }
    }

    inner class InputCell(initialValue: T) : Cell<T>() {
        override var value: T by Delegates.observable(initialValue) { _, _, _ ->
            // When the value changes, update any dependent ComputeCell objects

            dependentComputeCells.forEach { it.update() }
            dependentComputeCells.forEach { it.fireCallbacks() }
        }
    }

    inner class ComputeCell(private vararg val input_cells: Cell<T>, private val computeFunction: (List<T>) -> T) :
        Cell<T>() {

        override var value = computeFunction(input_cells.map { it.value })
        private val callbacks = mutableListOf<(T) -> Unit>()
        private var oldvalue = value

        init {
            input_cells.forEach { it.addDependentComputeCell(this) }
            update()
        }

        fun update() {
            val inputValues = input_cells.map { it.value }
            val newValue = computeFunction(inputValues)
            if (newValue != value) {
                value = newValue
                dependentComputeCells.forEach { it.update() }
            }
        }

        fun addCallback(callback: (T) -> Unit): Subscription {
            callbacks.add(callback)
            return object : Subscription {
                override fun cancel() {
                    callbacks.remove(callback)
                }
            }
        }

        fun fireCallbacks() {
            if (value == oldvalue) return
            oldvalue = value
            callbacks.forEach { it(value) }
            dependentComputeCells.forEach { it.fireCallbacks() }
        }

    }


}

