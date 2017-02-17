package KotlinDotNet.lexer

sealed class Token(val value : String){
    class HardKeywordToken(value: String) : Token(value)
    class SoftKeywordOrSymbolToken(value: String) : Token(value)
    class SoftKeywordModifierOrSymbolToken(value: String) : Token(value)
    class SymbolToken(value: String) : Token(value)
    class NamedSymbolToken(value: String) : Token(value)
    class SingleLineComment(value: String) : Token(value)
    class MultiLineComment(value: String) : Token(value)
    class NoEscapeStringLiteral(value: String) : Token(value)
    class RegularStringPart(value: String) : Token(value)
    class ErrorToken(value: String, val corruptedToken : Token?, val lexerError : LexerError) : Token(value)
    class EscapeSequence(value: String) : Token(value)
    class UnicodeEscapeSequence(value: String) : Token(value)
}

enum class LexerError {
    IllegalEscapeSequence,
    ClosingQuoteMissing
}
