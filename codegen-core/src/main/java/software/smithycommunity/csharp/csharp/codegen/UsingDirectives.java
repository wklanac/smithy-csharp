package software.smithycommunity.csharp.csharp.codegen;

import software.amazon.smithy.codegen.core.ImportContainer;
import software.amazon.smithy.codegen.core.Symbol;
import com.google.common.base.Strings;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * C# specific implementation for collecting groups of using directives for a single target namespace.
 *
 * Global using directives and static using directives are not supported.
 *
 * You can assume any symbol imports you request are checked for namespace conflicts,
 * but this class will not automatically re-alias any conflicts.
 *
 * Directives are transformed or ignored using the following rules for C# namespaces:
 * - Symbols of the same or of a parent namespace relative to the target namespace to not require directives
 * - Symbols of a namespace descending from the target require a directive bridging the lineage between namespaces
 * - Symbols of an adjacent namespace require a directive bridging the lineage from the point where lineages diverged
 */
public final class UsingDirectives implements ImportContainer {
    private final NamespacePath targetNamespacePath;
    private final Set<UsingDirective> usingDirectives = new TreeSet<UsingDirective>();

    public UsingDirectives(String targetNamespace) {
        targetNamespacePath = new NamespacePath(targetNamespace);
    }

    @Override
    public void importSymbol(Symbol symbol, String alias) {
        NamespacePath sourceNamespacePath = new NamespacePath(symbol.getNamespace());
        NamespacePath sourceRelativeToTargetPath = sourceNamespacePath.getPathTo(targetNamespacePath);

        if (!sourceRelativeToTargetPath.equals(NamespacePath.EMPTY_PATH)) {
            UsingDirective usingDirective = new UsingDirective(symbol.getNamespace(),
                    symbol.getName(), alias);

            // TODO: check for conflicts before adding to set

            usingDirectives.add(usingDirective);
        }
    }
    
    @Override
    public String toString() {
        return usingDirectives.stream()
                .map(UsingDirective::toString)
                .collect(Collectors.joining("\n"));
    }

    private record UsingDirective (String namespace, String symbolName, String alias)
            implements Comparable<UsingDirective> {
        @Override
        public String toString(){
            boolean aliasProvided = !Strings.isNullOrEmpty(alias);
            String aliasComponent = aliasProvided ? "" : String.join(" ",alias, "=");
            String namespaceAndSymbolComponent = namespace + (aliasProvided ? "." + symbolName : "");
            return String.join("using", aliasComponent, namespaceAndSymbolComponent, ";");
        }

        @Override
        public int compareTo(UsingDirective usingDirective) {
            return this.toString().compareTo(usingDirective.toString());
        }
    }
}

