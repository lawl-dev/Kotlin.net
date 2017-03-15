package KotlinDotNet.lexer

import java.util.*

class TransactableStream<out T>(items : Iterable<T>) {
    private var index = 0
    private val snapshotIndexes = Stack<Int>()
    private val items: MutableList<T> = mutableListOf()
    val length = items.count() - index

    init {
        this.items.addAll(items.toList())
    }

    val current: T? get() = if (overflows(0)) null else this.items[index]

    fun consume() {
        index++
    }

    fun peek(offset: Int): T? = if (overflows(offset)) null else this.items[index + offset]

    fun takeSnapshot() {
        snapshotIndexes.push(index)
    }

    fun rollbackSnapshot() {
        index = snapshotIndexes.pop()
    }

    fun commitSnapshot() {
        snapshotIndexes.pop()
    }

    fun pushBack(count: Int){
        if(index - count < 0){
            throw IndexOutOfBoundsException()
        }
        index = index - count
    }

    private fun overflows(offset: Int) = items.count() <= index + offset
}