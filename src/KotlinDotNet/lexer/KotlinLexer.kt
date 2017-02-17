package KotlinDotNet.lexer

import KotlinDotNet.lexer.strategies.*

class KotlinLexer {
    fun lex(source : String) : Iterable<Token> {
        val result = mutableListOf<Token>()
        val tokenStream = TransactableStream<Char>(source.asIterable())

        val strategies = createLexerStrategies()
        while (tokenStream.current != null) {
            val strategy = strategies.firstOrNull { it.isMatch(tokenStream) } ?: throw KtLexerException("Unexpected Token ${tokenStream.current}")
            result.addAll(strategy.parseToken(tokenStream)!!)
        }
        return result
    }

    private fun createLexerStrategies() : Iterable<TokenLexerStrategy>{
        val strategies = mutableListOf<TokenLexerStrategy>()

        strategies.add(LexerSingleLineCommentStrategy())
        strategies.add(LexerMultiLineCommentStrategy())

        strategies.add(LexerKeywordStrategy(Tokens.package_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.as_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.typealias_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.class_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.this_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.super_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.val_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.var_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.fun_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.for_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.null_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.true_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.false_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.is_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.throw_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.return_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.break_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.continue_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.object_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.if_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.try_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.else_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.while_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.do_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.when_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.interface_keyword))

        strategies.add(LexerKeywordStrategy(Tokens.file_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.field_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.property_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.receiver_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.param_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.setparam_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.delegate_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.import_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.where_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.by_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.get_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.set_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.constructor_keyword))
        strategies.add(LexerKeywordStrategy(Tokens.init_keyword))

        strategies.add(LexerKeywordStrategy(Tokens.finally_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.abstract_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.enum_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.open_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.inner_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.override_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.private_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.public_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.internal_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.protected_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.catch_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.out_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.vararg_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.reified_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.dynamic_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.companion_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.sealed_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.final_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.data_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.inline_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.noinline_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.tailrec_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.external_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.annotation_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.crossinline_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.operator_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.infix_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.const_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.suspend_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.header_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.impl_ModifierKeyword))
        strategies.add(LexerKeywordStrategy(Tokens.crossinline_ModifierKeyword))

        strategies.add(LexerNoEscapeStringLiteralStrategy())
        strategies.add(LexerStringLiteralStrategy())
        strategies.add(LexerIntegerLiteralStrategy())
        strategies.add(LexerHexadecimalLiteralStrategy())
        strategies.add(LexerFloatLiteralStrategy())
        strategies.add(LexerSimpleNameStrategy())
        return strategies
    }
}