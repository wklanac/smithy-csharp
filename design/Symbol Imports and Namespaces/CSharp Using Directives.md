# Summary
There are a few design issues we have to consider when generating "using" statements for and working with namespaces in C#. C# imports serve a variety of purposes including:
- Taking classes, interfaces, structs, enums, delegates defined in one namespace and making them directly accessible within another, without having to specify the full class name path
- Creating aliases for specific types in a namespace
- (New) Bringing in scope static members of a class within a namespace
- (New) Globally bringing in scope namespace members across a compilation unit (typically a project)

Looking at the new (i.e. C# 10) features for using declarations, we should think upfront about whether these cases will come into play with our code generation. Static member imports are not a must have for code generation, as the tenants for this are static methods and I don't see symbols as intending to have methods as one of their tenants. Similarly, global declarations could be considered "nice" to have at a project level, but assuming most users will be using visual studio, which allows easy collapsing of using declarations, I don't see this as a game changer that we will need to account for. 

However, because of the above there are multiple ways to bring in dependencies, the using statements required for a given written file will depend on the intended namespace scope of the generated software within the target file. We will end up with one of the following cases for an arbitrary symbol of interest depending on its relationship to the target file:
- Exists in or descends from the same namespace: we do NOT need any using statement for bringing the symbol in scope
- Is in a parent namespace of the target: we must either qualify the remaining portion of the namespace before using the type of interest, or we can import the fully qualified namespace to use the type directly
- Has a lineage that branches from the target: we must either qualify the namespace starting with the level at which the namespace lineages diverged, or import the fully qualified namespace
	- E.g. - if our target will be in namespace A.B.C.F and the symbol is found in namespace A.B.G.Q and is called P, to use P in the target, we must either specify G.Q.P or provide a "using A.B.G.Q;" statement and directly use P in the target

You'll notice we've forgotten something important - we've neglected to talk about how we will handle aliasing and when we will endeavor to use aliasing. I think we should only use aliasing in situations where we can detect that there is a conflict with in-scope naming caused by importing types with two different namespace paths in such a way that it would put an ambiguous name for both in the same namespace, meaning the user wAbove should be enough for basic client generation.ould have to fully qualify which they want to use. So, prior to spitting out the full set of using directives for a file, the implementing class should check for internal conflicts and resolve these using aliases.

There is also the situation where we have two dependencies with the exact same namespace and type name. We cannot use using directives to resolve these, and instead will need extern aliasing. We will cross that bridge once we get to it, and until then we will explicitly check and fail if this event occurs as it could be treated as similar to a java classpath conflict issue. We shouldn't be expected to provide tooling to fix this.

# Appendix: Cases of Source and Target Namespace and Resulting Using Directives
## Example 1

source: a.b
target: c.d.e
output: a.b

diverged index: 0

## Example 2

source: a.b
target: a.b.c
output: ""

diverged index: NOT EXIST

## Example 3

source: a.b.c
target: a.b
output: c

diverged index: NOT EXIST

## Example 4

source: a.b.c
target: d.e
output: a.b.c

diverged index: 0

## Example 5

source: a.b.d
target: a.b.c
output: d

diverged index: 2