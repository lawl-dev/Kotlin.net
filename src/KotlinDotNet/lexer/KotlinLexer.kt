package KotlinDotNet.lexer

import KotlinDotNet.lexer.strategies.*

class KotlinLexer {
    fun lex(source : String) : Iterable<Token> {
        val result = mutableListOf<Token>()
        val tokenStream = TransactableStream<Char>(source.asIterable())

        val strategies = createLexerStrategies()
        while (tokenStream.current != null) {
            val strategy = strategies.firstOrNull { it.isMatch(tokenStream) } ?: throw KtLexerException("Unexpected Token ${tokenStream.current}")
            result.add(strategy.parseToken(tokenStream)!!)
        }
        return result
    }

    private fun createLexerStrategies() : Iterable<TokenLexerStrategy>{
        val strategies = mutableListOf<TokenLexerStrategy>()

        strategies.add(LexerSingleLineCommentStrategy())
        strategies.add(LexerMultiLineCommentStrategy())

        strategies.add(LexerKeywordStrategy(Tokens.Companion.package_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.as_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.typealias_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.class_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.this_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.super_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.val_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.var_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.fun_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.for_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.null_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.true_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.false_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.is_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.throw_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.return_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.break_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.continue_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.object_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.if_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.try_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.else_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.while_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.do_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.when_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.interface_keyword))

        strategies.add(LexerKeywordStrategy(Tokens.Companion.file_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.field_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.property_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.receiver_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.param_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.setparam_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.delegate_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.import_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.where_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.by_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.get_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.set_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.constructor_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.init_keyword))

        strategies.add(LexerKeywordStrategy(Tokens.Companion.finally_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.abstract_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.enum_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.open_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.inner_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.override_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.private_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.public_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.internal_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.protected_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.catch_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.out_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.vararg_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.reified_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.dynamic_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.companion_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.sealed_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.final_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.data_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.inline_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.noinline_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.tailrec_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.external_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.annotation_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.crossinline_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.operator_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.infix_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.const_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.suspend_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.header_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.impl_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.Companion.crossinline_ModifierKeyword))

        strategies.add(LexerNoEscapeStringLiteralStrategy())
        strategies.add(LexerStringLiteralStrategy())
        strategies.add(LexerIntegerLiteralStrategy())
        strategies.add(LexerHexadecimalLiteralStrategy())
        strategies.add(LexerFloatLiteralStrategy())
        return strategies
    }
}