package KotlinDotNet.lexer.strategies

import KotlinDotNet.lexer.Token
import KotlinDotNet.lexer.TransactableStream

class LexerKeywordStrategy(private val token : Token) : TokenLexerStrategy() {
    override fun parseToken(transactableStream: TransactableStream<Char>): Iterable<Token>? {
        for (c in token.value) {
            if(transactableStream.current == null || transactableStream.current != c){
                transactableStream.rollbackSnapshot()
                return null
            }
        }
        return listOf(token)
    }
}