Sources: 
https://learn.microsoft.com/en-us/dotnet/fundamentals/code-analysis/style-rules/ide0055
https://learn.microsoft.com/en-us/dotnet/csharp/language-reference/language-specification/documentation-comments
https://learn.microsoft.com/en-us/dotnet/csharp/fundamentals/coding-style/coding-conventions#style-guidelines
# Documentation and Commenting Syntax

C# documentation comments use XML and can be formatted using one of the following:
- Single line comments: `///`
- Multi-line delimited comments: `/** ... **/`

Regular comments which are not intended for documentation should use `//` and `/*` for single and multi-line comments respectively.

Any documentation within methods should use regular comments, and method, class, etc., labelling should use documentation comments.

# Formatting Style Guidelines
Code generated will follow the formatting defaults given in C# IDE docs:
- System namespace directives come first
- Do not separate different namespace directive groups
- Allman style indentation (i.e. newline before braces for all cases)
- Newline before `else`, `catch`, and `finally` statements required
- Newline before object initializers, anonymous type members, and between query expression clauses

There are some other additional guidelines microsoft prescribes:
- Use four spaces for indentation; do not use tabs
- Limit lines to 65 characters (we may want to bump to 120)
- Line breaks should occur BEFORE binary operators (e.g. `+`) if necessary
- Never comment inline
- Start comment text with an uppercase letter and end with a period
- Always add a space between the comment delimiter and text
- Try to only write one statement / declaration per line
- Add at least one blank line between method definitions and property definitions.
- Use parentheses to make expression clauses apparent (i.e. always box multiple clauses in distinguishing parentheses): `if ((startX > endX) && (startX > previousX))`
- Minimum one blank line between method definitions and property definitions
- Indent continuation lines if they are not already indented