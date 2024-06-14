package software.smithycommunity.csharp.csharp.codegen;

public record CSharpFormattingSettings (
        int maxLineCharacterLength,
        boolean useMultilineCommentForDocumentation,
        boolean putSystemUsingDirectivesFirst,
        boolean separateNamespaceDirectiveGroups
){
}
