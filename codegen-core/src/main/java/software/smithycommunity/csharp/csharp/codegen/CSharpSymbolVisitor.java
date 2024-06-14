package software.smithycommunity.csharp.csharp.codegen;

import org.apache.commons.lang3.NotImplementedException;
import software.amazon.smithy.codegen.core.Symbol;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.*;
import software.amazon.smithy.utils.CaseUtils;

public class CSharpSymbolVisitor implements SymbolProvider, ShapeVisitor<Symbol> {


    private static final String MODEL_DIRECTORY = "./Model";
    private final Model model;
    private final ServiceShape serviceShape;
    private final CSharpCodegenSettings cSharpCodegenSettings;
    private final NamespacePath modelNamespace;

    public CSharpSymbolVisitor(Model model, ServiceShape serviceShape,
                               CSharpCodegenSettings cSharpCodegenSettings) {
        this.model = model;
        this.serviceShape = serviceShape;
        this.cSharpCodegenSettings = cSharpCodegenSettings;
        this.modelNamespace = cSharpCodegenSettings.namespacePath().append("Model");
    }

    @Override
    public Symbol toSymbol(Shape shape) {
        return shape.accept(this);
    }

    @Override
    public Symbol blobShape(BlobShape blobShape) {
        // TODO - 3: Add streaming trait support which uses array backed memory stream
        return createSymbolBuilder(blobShape, "byte[]").build();
    }

    @Override
    public Symbol booleanShape(BooleanShape booleanShape) {
        return createSymbolBuilder(booleanShape, "bool").build();
    }

    @Override
    public Symbol listShape(ListShape listShape) {
        MemberShape member = listShape.getMember();

        return createSymbolBuilder(listShape, "List", new NamespacePath("System.Collections.Generic"))
                .addReference(toSymbol(member))
                .build();
    }

    @Override
    public Symbol mapShape(MapShape mapShape) {
        MemberShape keyMember = mapShape.getKey();
        Shape keyTargetShape = model.expectShape(keyMember.getTarget());
        if (!keyTargetShape.isStringShape()) {
            throw new IllegalArgumentException("Non string key shape provided for map type.");
        }

        return createSymbolBuilder(mapShape, "Dictionary", new NamespacePath("System.Collections.Generic"))
                .addReference(toSymbol(keyMember))
                .addReference(toSymbol(mapShape.getValue()))
                .build();
    }

    @Override
    public Symbol byteShape(ByteShape byteShape) {
        return createSymbolBuilder(byteShape, "byte").build();
    }

    @Override
    public Symbol shortShape(ShortShape shortShape) {
        return createSymbolBuilder(shortShape, "short").build();
    }

    @Override
    public Symbol integerShape(IntegerShape integerShape) {
        return createSymbolBuilder(integerShape, "int").build();
    }

    @Override
    public Symbol longShape(LongShape longShape) {
        return createSymbolBuilder(longShape, "long").build();
    }

    @Override
    public Symbol floatShape(FloatShape floatShape) {
        return createSymbolBuilder(floatShape, "float").build();
    }

    @Override
    public Symbol documentShape(DocumentShape documentShape) {
        NamespacePath runtimeNamespace = cSharpCodegenSettings.namespacePath().append("Runtime");

        return createSymbolBuilder(documentShape, "Document", runtimeNamespace)
                .build();
    }

    @Override
    public Symbol doubleShape(DoubleShape doubleShape) {
        return createSymbolBuilder(doubleShape, "double").build();
    }

    @Override
    public Symbol bigIntegerShape(BigIntegerShape bigIntegerShape) {
        return createSymbolBuilder(bigIntegerShape, "BigInteger", new NamespacePath("System.Numerics")).build();
    }

    @Override
    public Symbol bigDecimalShape(BigDecimalShape bigDecimalShape) {
        // TODO - 1: Support users supplying runtime types + assembly locations in CSharpCodegenSettings
        throw new NotImplementedException("User provided runtime types are not supported yet.");
    }

    @Override
    public Symbol operationShape(OperationShape operationShape) {
        String operationName = defaultSymbolNameForShape(operationShape);
        return createSymbolBuilder(operationShape, operationName, modelNamespace)
                .definitionFile(defaultDefinitionFile(operationName))
                .build();
    }

    @Override
    public Symbol resourceShape(ResourceShape resourceShape) {
        String resourceName = defaultSymbolNameForShape(resourceShape);
        return createSymbolBuilder(resourceShape, resourceName, modelNamespace)
                .definitionFile(defaultDefinitionFile(resourceName))
                .build();
    }

    @Override
    public Symbol serviceShape(ServiceShape serviceShape) {
        String serviceName = defaultSymbolNameForShape(serviceShape);
        return createSymbolBuilder(serviceShape, serviceName, modelNamespace)
                .definitionFile(defaultDefinitionFile(serviceName))
                .build();
    }

    @Override
    public Symbol stringShape(StringShape stringShape) {
        return createSymbolBuilder(stringShape, "string").build();
    }

    @Override
    public Symbol structureShape(StructureShape structureShape) {
        String structureName = defaultSymbolNameForShape(structureShape);
        return createSymbolBuilder(structureShape, structureName, modelNamespace)
                .definitionFile(defaultDefinitionFile(structureName))
                .build();
    }

    @Override
    public Symbol unionShape(UnionShape unionShape) {
        String unionName = defaultSymbolNameForShape(unionShape);
        return createSymbolBuilder(unionShape, unionName, modelNamespace)
                .definitionFile(defaultDefinitionFile(unionName))
                .build();
    }

    @Override
    public Symbol memberShape(MemberShape memberShape) {
        ShapeId memberTarget = memberShape.getTarget();
        Shape targetShape = model.expectShape(memberTarget);

        return toSymbol(targetShape);
    }

    @Override
    public Symbol timestampShape(TimestampShape timestampShape) {
        return createSymbolBuilder(timestampShape, "DateTime").build();
    }

    private Symbol.Builder createSymbolBuilder(Shape shape, String name){
        return Symbol.builder()
                .putProperty("shape", shape)
                .name(name);
    }

    private Symbol.Builder createSymbolBuilder(Shape shape, String name, NamespacePath namespacePath) {
        return createSymbolBuilder(shape, name)
                .namespace(namespacePath.toString(), String.valueOf(NamespacePath.NAMESPACE_DELIMITER));
    }

    private String defaultSymbolNameForShape(Shape shape) {
        String contextualShapeName = shape.getId().getName(serviceShape);
        return CaseUtils.toPascalCase(CaseUtils.toSnakeCase(contextualShapeName));
    }

    private static String defaultDefinitionFile(String shapeName) {
        return MODEL_DIRECTORY + shapeName + ".cs";
    }
}
