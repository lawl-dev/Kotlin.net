package KotlinDotNet.lexer.strategies

import KotlinDotNet.lexer.Token
import KotlinDotNet.lexer.TransactableStream

class LexerNoEscapeStringLiteralStrategy : TokenLexerStrategy() {
    override fun parseToken(transactableStream: TransactableStream<Char>): Iterable<Token>? {
        if(transactableStream.current != '"' || transactableStream.peek(1) != '"' || transactableStream.peek(2) != '"'){
            return null
        }
        var result = "\"\"\""
        transactableStream.consume()
        transactableStream.consume()
        transactableStream.consume()
        while (transactableStream.current != '"' && transactableStream.peek(1) != '"' && transactableStream.peek(2) != '"') {
            result += transactableStream.current
            transactableStream.consume()
        }
        transactableStream.consume()
        transactableStream.consume()
        transactableStream.consume()
        result += "\"\"\""
        return listOf(Token.NoEscapeStringLiteral(result))
    }
}