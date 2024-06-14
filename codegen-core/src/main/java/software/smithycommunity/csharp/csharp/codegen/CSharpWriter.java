package software.smithycommunity.csharp.csharp.codegen;

import software.amazon.smithy.codegen.core.Symbol;
import software.amazon.smithy.codegen.core.SymbolWriter;

// Placeholder for CSharp writer. Features will be added for docs etc, as we develop code patterns.
public class CSharpWriter extends SymbolWriter<CSharpWriter, UsingDirectives> {
    private static final String COMMENT_PREFIX = "//";
    private static final String DOC_COMMENT_START = "/**";
    private static final String DOC_COMMENT_PREFIX = " * ";
    private static final String DOC_COMMENT_END = "**/";
    private static final String OPENING_BRACE = "{";

    public CSharpWriter(UsingDirectives importContainer,
                        CSharpFormattingSettings cSharpFormattingSettings) {
        super(importContainer);
    }

    public CSharpWriter addUsingDirective(Symbol symbol, String alias) {
        getImportContainer().importSymbol(symbol, alias);
        return this;
    }

    public CSharpWriter addSingleLineComment(String comment) {
        write(String.join(" ", COMMENT_PREFIX, comment));
        return this;
    }

    public CSharpWriter writeDocumentationComment(Runnable runnable) {
        pushState();
        write(DOC_COMMENT_START);
        setNewlinePrefix(DOC_COMMENT_PREFIX);
        runnable.run();
        setNewlinePrefix("");
        write(DOC_COMMENT_END);
        popState();
        return this;
    }

    public CSharpWriter openAllmanBlock(String string){
        write(string);
        write(OPENING_BRACE);
        indent();
        return this;
    }


}
