package software.smithycommunity.dotnet.csharp.codegen;

import software.amazon.smithy.codegen.core.ImportContainer;
import software.amazon.smithy.codegen.core.Symbol;
import com.google.common.base.Strings;

public final class UsingDirectives implements ImportContainer {
    private String targetNamespace;

    public UsingDirectives(String targetNamespace) {
        this.targetNamespace = targetNamespace;
    }

    @Override
    public void importSymbol(Symbol symbol, String s) {

    }

    record UsingDirective (String namespace, String symbolName, String alias){
        @Override
        public String toString(){
            boolean aliasProvided = !Strings.isNullOrEmpty(alias);
            String aliasComponent = aliasProvided ? "" : String.join(" ",alias, "=");
            String namespaceAndSymbolComponent = namespace + (aliasProvided ? "." + symbolName : "");
            return String.join("using", aliasComponent, namespaceAndSymbolComponent);
        }
    }
}

