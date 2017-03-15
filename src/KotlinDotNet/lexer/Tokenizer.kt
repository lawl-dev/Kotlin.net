package KotlinDotNet.lexer

import java.util.*

data class State(val stateType: StateType, val lBraceCount: Int)
enum class StateType(val exclusiv: Boolean = false) {
    Default,
    RawString,
    String,
    ShortTemplateEntry
}
class Tokenizer(val scanner: Scanner) {
    private val states = Stack<State>()
    private var lBraceCount = 0
    private var commentStart: Int = 0
    private var commentDepth: Int = 0
    private var currentState: StateType = StateType.Default

    fun tokenize() {
        while (!scanner.empty) {
            tokenize(this.scanner::scanThreeQuote) {
                pushState(StateType.RawString)
                Token.OpenQuote(it)
            }
            contextualTokenizeChar(StateType.RawString, this.scanner::scanNewLine){
                Token.RegularStringPart(it)
            }
            contextualTokenizeChar(StateType.RawString, this.scanner::scanQuote){
                Token.RegularStringPart(it)
            }
            contextualTokenizeChar(StateType.RawString, this.scanner::scanForwardSlash){
                Token.RegularStringPart(it)
            }
            contextualTokenize(StateType.RawString, this.scanner::scanThreeOrMoreQuote){
                val length = it.length
                if(length <= 3){
                    popState()
                    Token.ClosingQuote(it)
                }
                else
                {
                    this.scanner.pushBack(3)
                    Token.RegularStringPart(it)
                }
            }
            tokenizeChar(this.scanner::scanQuote){
                pushState(StateType.String)
                Token.OpenQuote(it)
            }
            contextualTokenizeChar(StateType.String, this.scanner::scanNewLine){
                popState()
                this.scanner.pushBack(1)
                Token.DanglingNewLine(it)
            }
            contextualTokenizeChar(StateType.String, this.scanner::scanQuote){
                popState()
                Token.ClosingQuote(it)
            }
            contextualTokenize(StateType.String, this.scanner::scanEscapeSequence){
                Token.EscapeSequence(it)
            }
            contextualTokenize(StateType.String, this.scanner::scanRegularStringPart){
                Token.RegularStringPart(it)
            }
            contextualTokenize(StateType.RawString, this.scanner::scanRegularStringPart){
                Token.RegularStringPart(it)
            }
            contextualTokenize(StateType.String, this.scanner::scanShortTemplateEntry){
                pushState(StateType.ShortTemplateEntry)
                this.scanner.pushBack(it.length - 1)
                Token.ShortTemplateEntryStart(it)
            }
            contextualTokenize(StateType.RawString, this.scanner::scanShortTemplateEntry){
                pushState(StateType.ShortTemplateEntry)
                this.scanner.pushBack(it.length - 1)
                Token.ShortTemplateEntryStart(it)
            }
        }
    }

    private fun contextualTokenizeChar(stateContext: StateType, scanner: () -> Char?, tokenFactory: (String) -> Token) = contextualTokenize(stateContext, {scanner()?.toString()}, tokenFactory)
    private fun contextualTokenize(stateContext: StateType, scanner: () -> String?, tokenFactory: (String) -> Token): Token? {
        if(this.currentState != stateContext){
            return null
        }
        val result = scanner() ?: return null
        return tokenFactory(result)
    }

    private fun tokenizeChar(scanner: () -> Char?, tokenFactory: (String) -> Token) = tokenize({ scanner()?.toString() }, tokenFactory)
    private fun tokenize(scanner: () -> String?, tokenFactory: (String) -> Token): Token? {
        val result = scanner() ?: return null
        return tokenFactory(result)
    }

    private fun pushState(stateType: StateType) {
        states.push(State(currentState, lBraceCount))
        lBraceCount = 0
        currentState = stateType
    }

    private fun popState(){
        val state = states.pop()
        lBraceCount = state.lBraceCount
        currentState = state.stateType
    }
}
