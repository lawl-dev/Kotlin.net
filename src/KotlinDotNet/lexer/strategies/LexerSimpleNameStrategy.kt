package KotlinDotNet.lexer.strategies

import KotlinDotNet.lexer.Token
import KotlinDotNet.lexer.TransactableStream

class LexerSimpleNameStrategy : TokenLexerStrategy() {
    override fun parseToken(transactableStream: TransactableStream<Char>): Iterable<Token>? {
        if (transactableStream.current?.isJavaIdentifierStart() ?: false) {
            return null
        }
        var result = transactableStream.current.toString()
        while (transactableStream.current?.isJavaIdentifierPart() ?: false) {
            result += transactableStream.current
        }
        return listOf(Token.NamedSymbolToken(result))
    }
}