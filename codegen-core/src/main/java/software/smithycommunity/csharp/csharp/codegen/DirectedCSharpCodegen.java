package software.smithycommunity.csharp.csharp.codegen;

import org.apache.commons.lang3.NotImplementedException;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.codegen.core.directed.*;

public class DirectedCSharpCodegen implements DirectedCodegen<CSharpCodegenContext, CSharpCodegenSettings, CSharpIntegration> {
    @Override
    public SymbolProvider createSymbolProvider(CreateSymbolProviderDirective<CSharpCodegenSettings> createSymbolProviderDirective) {
        throw new NotImplementedException();
    }

    @Override
    public CSharpCodegenContext createContext(CreateContextDirective<CSharpCodegenSettings, CSharpIntegration> createContextDirective) {
        throw new NotImplementedException();
    }

    @Override
    public void generateService(GenerateServiceDirective<CSharpCodegenContext, CSharpCodegenSettings> generateServiceDirective) {
        throw new NotImplementedException();
    }

    @Override
    public void generateStructure(GenerateStructureDirective<CSharpCodegenContext, CSharpCodegenSettings> generateStructureDirective) {
        throw new NotImplementedException();
    }

    @Override
    public void generateError(GenerateErrorDirective<CSharpCodegenContext, CSharpCodegenSettings> generateErrorDirective) {
        throw new NotImplementedException();
    }

    @Override
    public void generateUnion(GenerateUnionDirective<CSharpCodegenContext, CSharpCodegenSettings> generateUnionDirective) {
        throw new NotImplementedException();
    }

    @Override
    public void generateEnumShape(GenerateEnumDirective<CSharpCodegenContext, CSharpCodegenSettings> generateEnumDirective) {
        throw new NotImplementedException();
    }

    @Override
    public void generateIntEnumShape(GenerateIntEnumDirective<CSharpCodegenContext, CSharpCodegenSettings> generateIntEnumDirective) {
        throw new NotImplementedException();
    }
}
