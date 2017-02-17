package KotlinDotNet.lexer.strategies

import KotlinDotNet.lexer.Token
import KotlinDotNet.lexer.TransactableStream

class LexerIntegerLiteralStrategy : TokenLexerStrategy() {
    override fun parseToken(transactableStream: TransactableStream<Char>): Iterable<Token>? {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}