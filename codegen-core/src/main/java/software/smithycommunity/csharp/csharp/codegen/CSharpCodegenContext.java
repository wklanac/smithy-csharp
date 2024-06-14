package software.smithycommunity.csharp.csharp.codegen;

import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.codegen.core.CodegenContext;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.codegen.core.WriterDelegator;
import software.amazon.smithy.model.Model;

import java.util.List;

public record CSharpCodegenContext(
        Model model,
        CSharpCodegenSettings settings,
        SymbolProvider symbolProvider,
        FileManifest fileManifest,
        WriterDelegator<CSharpWriter> writerDelegator,
        List<CSharpIntegration> integrations
) implements CodegenContext<CSharpCodegenSettings, CSharpWriter, CSharpIntegration> {
}
