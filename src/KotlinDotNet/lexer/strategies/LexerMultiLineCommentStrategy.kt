package KotlinDotNet.lexer.strategies

import KotlinDotNet.lexer.Token
import KotlinDotNet.lexer.TransactableStream

class LexerMultiLineCommentStrategy : TokenLexerStrategy() {
    override fun parseToken(transactableStream: TransactableStream<Char>): Iterable<Token>? {
        if(transactableStream.current != '/' || transactableStream.peek(1) != '*')
        {
            return null
        }
        transactableStream.consume()
        transactableStream.consume()
        var result = """\*"""
        while (transactableStream.current != null && (transactableStream.current != '*' && transactableStream.peek(1) != '/'))
        {
            transactableStream.consume()
            result += transactableStream.current
        }
        return listOf(Token.MultiLineComment(result))
    }
}