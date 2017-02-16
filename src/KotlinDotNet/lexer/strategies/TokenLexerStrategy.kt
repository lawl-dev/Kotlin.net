package KotlinDotNet.lexer.strategies

import KotlinDotNet.lexer.Token
import KotlinDotNet.lexer.TransactableStream

abstract class TokenLexerStrategy {
    fun isMatch(transactableStream : TransactableStream<Char>) : Boolean {
        transactableStream.takeSnapshot()
        val isMatch = parseToken(transactableStream) != null
        transactableStream.rollbackSnapshot()
        return isMatch
    }

    abstract fun parseToken(transactableStream : TransactableStream<Char>) : Token?
}