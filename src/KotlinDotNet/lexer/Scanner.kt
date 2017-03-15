package KotlinDotNet.lexer


class Scanner(private val stream: TransactableStream<Char>) {
    val empty get() = stream.length > 0

    fun pushBack(count: Int) = stream.pushBack(count)

    fun scanNewLine() = scan('\n')
    fun scanQuote() = scan('"')
    fun scanForwardSlash() = scan('\\')

    fun scanDigits(): String? {
        val digit = scanDigit() ?: return null
        return digit.toString() + scanZeroOrMore { scanDigitOrUnderscore() }.joinToString()
    }

    fun scanPlainIdentifier(): String? {
        val letter = scanLetter() ?: return null
        return letter.toString() + scanZeroOrMore { scanIdentifierPart() }.joinToString()
    }

    fun scanEscapedIdentifier(): String? {
        return transaction {
            val escapedIdentifierStart = scan('`') ?: return@transaction null
            val identifierName = scanOneOrMore { scan { it != '`' && it != '\n' } }?.joinToString() ?: return@transaction null
            val escapedIdentifierEnd = scan('`')?.toString() ?: return@transaction null
            val ret = escapedIdentifierStart + identifierName + escapedIdentifierEnd
            return@transaction ret
        }
    }

    fun scanFieldIdenfifier(): String? {
        return transaction {
            val prefix = scan('$') ?: return@transaction null
            val identifier = scanIdentifier() ?: return@transaction null
            return@transaction  prefix.toString() + identifier
        }
    }

    fun scanEOLComment(): String? {
        if (stream.current != '/' || stream.peek(1) != '/') {
            return null
        }
        stream.consume()
        stream.consume()
        val comment = scanZeroOrMore { scan('\\') }.joinToString()
        return "//" + comment
    }

    fun scanShebangComment(): Nothing = throw NotImplementedError()

    fun scanIntegerLiteral() = scanDecimalIntegerLiteral() ?: scanHexIntegerLiteral() ?: scanBigIntegerLiteral()

    fun scanBigIntegerLiteral(): String? {
        return transaction {
            val zero = scan('0')?.toString() ?: return@transaction null
            val x = scan('b', 'B') ?: return@transaction null
            val literal = scanOneOrMore { scanHexDigitOrUnderscore() } ?: return@transaction null
            val suffix = scanLongSuffix()
            return@transaction arrayOf(zero, x, literal, suffix).filterNotNull().joinToString()
        }
    }

    fun scanHexIntegerLiteral(): String? {
        return transaction {
            val zero = scan('0')?.toString() ?: return@transaction null
            val x = scan('x', 'X') ?: return@transaction null
            val literal = scanZeroOrMore { scanHexDigitOrUnderscore() }
            val suffix = scanLongSuffix()
            return@transaction arrayOf(zero, x, literal, suffix).filterNotNull().joinToString()
        }
    }

    fun scanDecimalIntegerLiteral(): String? {
        return transaction {
            val zero = scan('0')?.toString()
            var literal = zero
            if (zero == null) {
                val firstDigit = scan('1'..'9')?.toString()
                if (firstDigit != null) {
                    literal = firstDigit + scanZeroOrMore { scanDigitOrUnderscore() }.joinToString()
                    return@transaction literal
                } else {
                    return@transaction null
                }
            }
            val suffix = scanLongSuffix()
            return@transaction arrayOf(literal, suffix).filterNotNull().joinToString()
        }
    }

    fun scanDoubleLiteral() = scanFloatingPointLiteral1() ?: scanFloatingPointLiteral2() ?: scanFloatingPointLiteral3() ?: scanFloatingPointLiteral4()

    fun scanFloatingPointLiteral1(): String? {
        return transaction {
            val part1 = scanDigits()
            val point = scan('.') ?: return@transaction null
            val part2 = scanDigits() ?: return@transaction null
            val opExPart = scanExponentPart()
            val floatingPointLiteralSuffix = scanFloatingPointLiteralSuffix()
            return@transaction arrayOf(part1, point, part2, opExPart, floatingPointLiteralSuffix).filterNotNull().joinToString()
        }
    }

    fun scanFloatingPointLiteral2(): String? {
        return transaction {
            val point = scan('.') ?: return@transaction null
            val part1 = scanDigits() ?: return@transaction null
            val opExPart = scanExponentPart()
            val floatingPointLiteralSuffix = scanFloatingPointLiteralSuffix()
            return@transaction arrayOf(point, part1, opExPart, floatingPointLiteralSuffix).filterNotNull().joinToString()
        }
    }

    fun scanFloatingPointLiteral3(): String? {
        return transaction {
            val digits = scanDigits() ?: return@transaction null
            val exponentPart = scanExponentPart() ?: return@transaction null
            val opFloatingPointLiteralSuffix = scanFloatingPointLiteralSuffix()
            return@transaction arrayOf(digits, exponentPart, opFloatingPointLiteralSuffix).filterNotNull().joinToString()
        }
    }

    fun scanFloatingPointLiteral4(): String? {
        return transaction {
            val digits = scanDigits() ?: return@transaction null
            val floatingPointLiteralSuffix = scanFloatingPointLiteralSuffix() ?: return@transaction null
            return@transaction arrayOf(digits, floatingPointLiteralSuffix).joinToString()
        }
    }

    fun scanFloatingPointLiteralSuffix() = scan('f', 'F')

    fun scanExponentPart(): String? {
        val e = scan('e', 'E')
        if (e == null) {
            return null
        }
        val op = scan('+', '-').toString()
        val digitOrUnderscore = scanZeroOrMore { scanDigitOrUnderscore() }.joinToString()
        return arrayOf(e, op, digitOrUnderscore).filterNotNull().joinToString()
    }

    fun scanCharacterLiteral(): String? {
        return transaction {
            val start = scan('\'') ?: return@transaction null
            val literal = scanZeroOrMore { scanCharacterLiteralPart() }.joinToString()
            val end = scan('\'', '\\')
            return@transaction arrayOf(start, literal, end).filterNotNull().joinToString()
        }
    }

    fun scanThreeOrMoreQuote(): String? {
        return transaction {
            val start = scanThreeQuote() ?: return@transaction null
            val other = scanZeroOrMore { scan('\"') }.joinToString()
            return@transaction arrayOf(start, other).joinToString()
        }
    }

    fun scanThreeQuote(): String? {
        if(stream.current != '\"' || stream.peek(1) != '"' || stream.peek(2) != '"'){
            return null
        }
        stream.consume()
        stream.consume()
        stream.consume()
        return "\"\"\""
    }

    fun scanRegularStringPart(): String? {
        fun isValidStringPartChar(char: Char) = char != '\\' && char != '"' && char != '\n' && char != '$'
        return transaction {
            val oneOrMoreParts = scanOneOrMore { scan(::isValidStringPartChar) } ?: return@transaction null
            return@transaction oneOrMoreParts.joinToString()
        }
    }

    fun scanShortTemplateEntry(): String? {
        return transaction {
            val prefix = scan('$') ?: return@transaction null
            val scanIdentifier = scanIdentifier() ?: return@transaction null
            return@transaction prefix + scanIdentifier
        }
    }

    fun scanLonelyDollar() = scan('$')

    fun scanLongTemplateEntryStart(): String? {
        return transaction {
            val start: Char = scan('$') ?: return@transaction null
            val start2: Char = scan('{') ?: return@transaction null
            return@transaction start.toString() + start2
        }
    }

    fun scanCharacterLiteralPart(): String? {
        fun isValidCharacterLiteral(char: Char) = char != '\\' || char != '\'' || char != '\n'
        var literal = scan(::isValidCharacterLiteral)?.toString()
        if (literal == null) {
            literal = scanEscapeSequence()
        }
        return literal
    }

    fun scanEscapeSequence(): String? {
        return transaction {
            val prefix = scan('\\') ?: return@transaction null

            val unicode = transaction {
                scanUnicodeEscapeSequence()
            }
            if (unicode != null) {
                return@transaction prefix.toString() + unicode
            }
            val anyOtherNotNewline = scan { it != '\n' } ?: return@transaction null
            return@transaction prefix + anyOtherNotNewline.toString()
        }
    }

    fun scanUnicodeEscapeSequence(): String? {
        val u = scan('u')
        if (u != null) {
            val hexDigit1 = scanHexDigit() ?: return null
            val hexDigit2 = scanHexDigit() ?: return null
            val hexDigit3 = scanHexDigit() ?: return null
            val hexDigit4 = scanHexDigit() ?: return null
            return arrayOf(u, hexDigit1, hexDigit2, hexDigit3, hexDigit4).joinToString()
        }
        return null
    }

    fun scanLongSuffix() = scan('l', 'L')
    fun scanIdentifier() = transaction { scanPlainIdentifier() } ?: scanEscapedIdentifier()

    fun scanIdentifierPart() = transaction {
        scan(Char::isDigit)
    } ?: scanLetter()

    fun scanLetter() = transaction {
        scan(Char::isJavaIdentifierStart)
    } ?: scan('_')

    fun scanWhitespace() = scan(' ', '\n', '\t', '\u000c')
    fun scanHexDigit() = scan(('0'..'9') + ('a'..'f') + ('A'..'F'))
    fun scanHexDigitOrUnderscore() = scan(('0'..'9') + ('a'..'f') + ('A'..'F') + '_')
    fun scanDigit() = scan('0'..'9')
    fun scanDigitOrUnderscore() = scan(('0'..'9') + '_')

    fun scan(predicate: (Char) -> Boolean): Char? {
        val current = stream.current
        if (current != null && predicate(current)) {
            stream.consume()
            return current
        }
        return null
    }
    fun scan(set: CharRange) = scan(*set.toList().toCharArray())
    fun scan(set: List<Char>) = scan(*set.toCharArray())
    fun scan(vararg set: Char): Char? {
        for (item in set) {
            val scan = stream.current
            if (scan != null) {
                stream.consume()
                return scan
            }
        }
        return null
    }

    private fun <T> scanZeroOrMore(scanner: () -> T?): Iterable<T> {
        val ret = mutableListOf<T>()
        do {
            val scanned = scanner()
            if (scanned != null) {
                ret.add(scanned)
            }
        } while (scanned != null)
        return ret
    }

    private fun scanOneOrMore(scanner: () -> Char?): Iterable<Char>? {
        val ret = mutableListOf<Char>()
        val scanned = scanner() ?: return null
        ret.add(scanned)
        do {
            val scanned = scanner()
            if (scanned != null) {
                ret.add(scanned)
            }
        } while (scanned != null)
        return ret
    }

    private fun <T> transaction(scanner: () -> T?): T? {
        stream.takeSnapshot()
        val result = scanner()
        if (result == null) {
            stream.rollbackSnapshot()
            return null
        }
        stream.commitSnapshot()
        return result
    }
}

private val TransactableStream<Char>.isEOF: Boolean get() = this.current == null
