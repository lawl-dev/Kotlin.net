package KotlinDotNet.lexer

class Tokens {
    companion object {
        val keywords : Array<Token.HardKeywordToken> get() = arrayOf(
                package_keyword,
                as_keyword,
                typealias_keyword,
                class_keyword,
                this_keyword,
                super_keyword,
                val_keyword,
                var_keyword,
                fun_keyword,
                for_keyword,
                null_keyword,
                true_keyword,
                false_keyword,
                is_keyword,
                throw_keyword,
                return_keyword,
                break_keyword,
                continue_keyword,
                object_keyword,
                if_keyword,
                try_keyword,
                else_keyword,
                while_keyword,
                do_keyword,
                when_keyword,
                interface_keyword,
                notIn_keyword,
                notIs_keyword
        )

        val package_keyword = Token.HardKeywordToken("package")
        val as_keyword = Token.HardKeywordToken("as")
        val typealias_keyword = Token.HardKeywordToken("typealias")
        val class_keyword = Token.HardKeywordToken("class")
        val this_keyword = Token.HardKeywordToken("this")
        val super_keyword = Token.HardKeywordToken("super")
        val val_keyword = Token.HardKeywordToken("val")
        val var_keyword = Token.HardKeywordToken("var")
        val fun_keyword = Token.HardKeywordToken("fun")
        val for_keyword = Token.HardKeywordToken("for")
        val null_keyword = Token.HardKeywordToken("null")
        val true_keyword = Token.HardKeywordToken("true")
        val false_keyword = Token.HardKeywordToken("false")
        val is_keyword = Token.HardKeywordToken("is")
        val throw_keyword = Token.HardKeywordToken("throw")
        val return_keyword = Token.HardKeywordToken("return")
        val break_keyword = Token.HardKeywordToken("break")
        val continue_keyword = Token.HardKeywordToken("continue")
        val object_keyword = Token.HardKeywordToken("object")
        val if_keyword = Token.HardKeywordToken("if")
        val try_keyword = Token.HardKeywordToken("try")
        val else_keyword = Token.HardKeywordToken("else")
        val while_keyword = Token.HardKeywordToken("while")
        val do_keyword = Token.HardKeywordToken("do")
        val when_keyword = Token.HardKeywordToken("when")
        val interface_keyword = Token.HardKeywordToken("interface")
        val notIn_keyword = Token.HardKeywordToken("!in")
        val notIs_keyword = Token.HardKeywordToken("!is")

        val softKeywords: Array<Token.SoftKeywordOrSymbolToken> get() = arrayOf(
                file_keyword,
                field_keyword,
                property_keyword,
                receiver_keyword,
                setparam_keyword,
                delegate_keyword,
                import_keyword,
                where_keyword,
                by_keyword,
                get_keyword,
                set_keyword,
                constructor_keyword,
                init_keyword
        )

        val file_keyword = Token.SoftKeywordOrSymbolToken("file")
        val field_keyword = Token.SoftKeywordOrSymbolToken("field")
        val property_keyword = Token.SoftKeywordOrSymbolToken("property")
        val receiver_keyword = Token.SoftKeywordOrSymbolToken("receiver")
        val param_keyword = Token.SoftKeywordOrSymbolToken("param")
        val setparam_keyword = Token.SoftKeywordOrSymbolToken("setparam")
        val delegate_keyword = Token.SoftKeywordOrSymbolToken("delegate")
        val import_keyword = Token.SoftKeywordOrSymbolToken("import")
        val where_keyword = Token.SoftKeywordOrSymbolToken("where")
        val by_keyword = Token.SoftKeywordOrSymbolToken("by")
        val get_keyword = Token.SoftKeywordOrSymbolToken("get")
        val set_keyword = Token.SoftKeywordOrSymbolToken("set")
        val constructor_keyword = Token.SoftKeywordOrSymbolToken("constructor")
        val init_keyword = Token.SoftKeywordOrSymbolToken("init")

        val modifierKeywords: Array<Token.SoftKeywordModifierOrSymbolToken> get() = arrayOf(
                finally_ModifierKeyword,
                abstract_ModifierKeyword,
                enum_ModifierKeyword,
                open_ModifierKeyword,
                inner_ModifierKeyword,
                override_ModifierKeyword,
                private_ModifierKeyword,
                public_ModifierKeyword,
                internal_ModifierKeyword,
                protected_ModifierKeyword,
                catch_ModifierKeyword,
                out_ModifierKeyword,
                vararg_ModifierKeyword,
                reified_ModifierKeyword,
                dynamic_ModifierKeyword,
                companion_ModifierKeyword,
                sealed_ModifierKeyword,
                final_ModifierKeyword,
                data_ModifierKeyword,
                inline_ModifierKeyword,
                noinline_ModifierKeyword,
                tailrec_ModifierKeyword,
                external_ModifierKeyword,
                annotation_ModifierKeyword,
                crossinline_ModifierKeyword,
                operator_ModifierKeyword,
                infix_ModifierKeyword,
                const_ModifierKeyword,
                suspend_ModifierKeyword,
                header_ModifierKeyword,
                impl_ModifierKeyword
        )

        val finally_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("finally")
        val abstract_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("abstract")
        val enum_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("enum")
        val open_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("open")
        val inner_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("inner")
        val override_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("override")
        val private_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("private")
        val public_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("public")
        val internal_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("internal")
        val protected_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("protected")
        val catch_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("catch")
        val out_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("out")
        val vararg_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("vararg")
        val reified_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("reified")
        val dynamic_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("dynamic")
        val companion_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("companion")
        val sealed_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("sealed")
        val final_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("final")
        val data_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("data")
        val inline_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("inline")
        val noinline_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("noinline")
        val tailrec_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("tailrec")
        val external_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("external")
        val annotation_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("annotation")
        val crossinline_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("crossinline")
        val operator_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("operator")
        val infix_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("infix")
        val const_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("const")
        val suspend_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("suspend")
        val header_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("header")
        val impl_ModifierKeyword = Token.SoftKeywordModifierOrSymbolToken("impl")

        val symbols: Array<Token.SymbolToken> get() = arrayOf(
                LBRACKET,
                RBRACKET,
                LBRACE,
                RBRACE,
                LPAR,
                RPAR,
                DOT,
                PLUSPLUS,
                MINUSMINUS,
                MUL,
                PLUS,
                MINUS,
                EXCL,
                DIV,
                PERC,
                LT,
                GT,
                LTEQ,
                GTEQ,
                EQEQEQ,
                ARROW,
                DOUBLE_ARROW,
                EXCLEQEQEQ,
                EQEQ,
                EXCLEQ,
                EXCLEXCL,
                ANDAND,
                OROR,
                SAFE_ACCESS,
                ELVIS,
                QUEST,
                COLONCOLON,
                COLON,
                SEMICOLON,
                DOUBLE_SEMICOLON,
                RANGE,
                EQ,
                MULTEQ,
                DIVEQ,
                PERCEQ,
                PLUSEQ,
                MINUSEQ,
                HASH,
                AT,
                COMMA,
                WHITESPACE
        )

        val LBRACKET = Token.SymbolToken("[")
        val RBRACKET = Token.SymbolToken("]")
        val LBRACE = Token.SymbolToken("{")
        val RBRACE = Token.SymbolToken("}")
        val LPAR = Token.SymbolToken("(")
        val RPAR = Token.SymbolToken(")")
        val DOT = Token.SymbolToken(".")
        val PLUSPLUS = Token.SymbolToken("++")
        val MINUSMINUS = Token.SymbolToken("--")
        val MUL = Token.SymbolToken("*")
        val PLUS = Token.SymbolToken("+")
        val MINUS = Token.SymbolToken("-")
        val EXCL = Token.SymbolToken("!")
        val DIV = Token.SymbolToken("/")
        val PERC = Token.SymbolToken("%")
        val LT = Token.SymbolToken("<")
        val GT = Token.SymbolToken(">")
        val LTEQ = Token.SymbolToken("<=")
        val GTEQ = Token.SymbolToken(">=")
        val EQEQEQ = Token.SymbolToken("===")
        val ARROW = Token.SymbolToken("->")
        val DOUBLE_ARROW = Token.SymbolToken("=>")
        val EXCLEQEQEQ = Token.SymbolToken("!==")
        val EQEQ = Token.SymbolToken("==")
        val EXCLEQ = Token.SymbolToken("!=")
        val EXCLEXCL = Token.SymbolToken("!!")
        val ANDAND = Token.SymbolToken("&&")
        val OROR = Token.SymbolToken("||")
        val SAFE_ACCESS = Token.SymbolToken("?.")
        val ELVIS = Token.SymbolToken("?:")
        val QUEST = Token.SymbolToken("?")
        val COLONCOLON = Token.SymbolToken("::")
        val COLON = Token.SymbolToken(":")
        val SEMICOLON = Token.SymbolToken(";")
        val DOUBLE_SEMICOLON = Token.SymbolToken(";;")
        val RANGE = Token.SymbolToken("..")
        val EQ = Token.SymbolToken("=")
        val MULTEQ = Token.SymbolToken("*=")
        val DIVEQ = Token.SymbolToken("/=")
        val PERCEQ = Token.SymbolToken("%=")
        val PLUSEQ = Token.SymbolToken("+=")
        val MINUSEQ = Token.SymbolToken("-=")
        val HASH = Token.SymbolToken("#")
        val AT = Token.SymbolToken("@")
        val COMMA = Token.SymbolToken(",")
        val WHITESPACE = Token.SymbolToken(" ")
        val NEWLINE = Token.SymbolToken("\r\n")
        val TAB = Token.SymbolToken("\t")
    }
}