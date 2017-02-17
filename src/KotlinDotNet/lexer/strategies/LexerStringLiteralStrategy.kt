package KotlinDotNet.lexer.strategies

import KotlinDotNet.lexer.*

class LexerStringLiteralStrategy : TokenLexerStrategy() {
    override fun parseToken(transactableStream: TransactableStream<Char>): Iterable<Token>? {
        if (transactableStream.current != '"') {
            return null
        }
        transactableStream.consume()
        var result = mutableListOf<Token>()
        result.add(Token.SymbolToken(""))
        do {
            val templateElement = parseStringTemplateElement(transactableStream)
            result.addAll(templateElement)
        } while (templateElement.any())
        if (transactableStream.current == '"') {
            transactableStream.consume()
        }
        else result.add(Token.ErrorToken("", null, LexerError.ClosingQuoteMissing))
        return result
    }

    fun parseStringTemplateElement(transactableStream: TransactableStream<Char>) : Iterable<Token> {
        val regularStringPart = parseRegularStringPart(transactableStream)
        if(regularStringPart != null)
        {
            return listOf(regularStringPart)
        }
        val shortTemplateEntry = parseShortTemplateEntry(transactableStream)
        if(shortTemplateEntry != null){
            return shortTemplateEntry
        }
        val escapeSequence = parseEscapeSequence(transactableStream)
        if(escapeSequence != null){
            return listOf(escapeSequence)
        }
        return emptyList()
    }

    fun parseRegularStringPart(transactableStream: TransactableStream<Char>): Token? {
        fun TransactableStream<Char>.isForbiddenRegularStringPartChar() = this.current == '\\' || this.current == '$' || this.current == '"' || (this.current == '\r' && this.peek(1) == '\n')
        if (transactableStream.isForbiddenRegularStringPartChar()) {
            return null
        }
        var result = ""
        while (transactableStream.current != null)
        {
            when
            {
                transactableStream.isForbiddenRegularStringPartChar() -> return Token.RegularStringPart(result)
                else -> result += transactableStream.current
            }
        }
        return Token.RegularStringPart(result)
    }

    fun parseShortTemplateEntry(transactableStream: TransactableStream<Char>) : Iterable<Token>? {
        if(transactableStream.current != '$'){
            return null
        }
        transactableStream.consume()
        val result = mutableListOf<Token>()
        result.add(Token.SymbolToken("$"))
        val thisKeywordStrategy = LexerKeywordStrategy(Tokens.this_keyword)
        if(thisKeywordStrategy.isMatch(transactableStream)) {
            result.addAll(thisKeywordStrategy.parseToken(transactableStream)!!)
            return result
        }
        val lexerSimpleNameStrategy = LexerSimpleNameStrategy()
        if(lexerSimpleNameStrategy.isMatch(transactableStream)) {
            result.addAll(lexerSimpleNameStrategy.parseToken(transactableStream)!!)
            return result
        }
        return null
    }

    fun parseEscapeSequence(transactableStream: TransactableStream<Char>) = parseUnicodeEscapeSequence(transactableStream) ?: parseRegularEscapeSequence(transactableStream)


    fun parseUnicodeEscapeSequence(transactableStream: TransactableStream<Char>): Token? {
        if(transactableStream.current != '\\' && transactableStream.peek(1) != 'u')
        {
            return null
        }
        transactableStream.consume()
        if(transactableStream.length < 4){
            return Token.ErrorToken("\\u", Token.UnicodeEscapeSequence("\\u"), LexerError.IllegalEscapeSequence)
        }

        var result = "\\u"
        transactableStream.takeSnapshot()
        for (x in 0..4){
            if(transactableStream.current !in '0'..'9' && transactableStream.current !in 'a'..'f' && transactableStream.current !in 'A'..'F' )
            {
                transactableStream.rollbackSnapshot()
                return Token.ErrorToken("\\u", Token.UnicodeEscapeSequence("\\u"), LexerError.IllegalEscapeSequence)
            }
            result += transactableStream.current
        }
        transactableStream.commitSnapshot()
        return Token.UnicodeEscapeSequence(result)
    }

    fun parseRegularEscapeSequence(transactableStream: TransactableStream<Char>): Token? {
        if(transactableStream.current != '\\')
        {
            return null
        }
        transactableStream.consume()
        var result = when(transactableStream.current){
            't', 'b', 'n', 'r', '\\', '$' -> Token.EscapeSequence("\\${transactableStream.current}")
            else -> Token.ErrorToken("\\${transactableStream.current}", Token.EscapeSequence("\\${transactableStream.current}"), LexerError.IllegalEscapeSequence)
        }
        transactableStream.consume()
        return result
    }

    fun parseLongTemplate(transactableStream: TransactableStream<Char>): Token? {
        throw NotImplementedError()
    }
}