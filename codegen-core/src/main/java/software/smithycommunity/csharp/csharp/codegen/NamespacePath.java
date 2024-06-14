package software.smithycommunity.csharp.csharp.codegen;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Representation of a C# namespace path, including functionality
 * required to find the relationship between different namespace paths.
 */
class NamespacePath {

    public static final NamespacePath EMPTY_PATH = new NamespacePath("");
    public static final char NAMESPACE_DELIMITER = '.';
    private static final String NAMESPACE_DELIMITER_PATTERN = "\\" + NAMESPACE_DELIMITER;


    private final String[] pathElements;

    NamespacePath(String path) {
        pathElements = path.split(NAMESPACE_DELIMITER_PATTERN);

        if (pathElements.length != StringUtils.countMatches(path, NAMESPACE_DELIMITER) + 1) {
            throw new IllegalArgumentException("Invalid namespace format provided.");
        }
    }

    NamespacePath(String[] pathElements) {
        this.pathElements = pathElements;
    }

    String[] getPathElements() {
        return pathElements;
    }

    @Override
    public String toString() {
        return String.join(String.valueOf(NAMESPACE_DELIMITER), pathElements);
    }

    NamespacePath getPathTo(NamespacePath targetNamespacePath) {
        int sourcePathLength = pathElements.length;
        String[] targetPathElements = targetNamespacePath.getPathElements();
        int targetPathLength = targetPathElements.length;

        if (this.equals(targetNamespacePath)) {
            return EMPTY_PATH;
        }

        boolean sourcePathIsShorterOrEqual = sourcePathLength <= targetPathLength;
        int shorterPathLength = Math.min(sourcePathLength, targetPathLength);

        OptionalInt indexDivergedAt = IntStream.range(0, shorterPathLength)
                .filter(index -> !Objects.equals(pathElements[index], targetPathElements[index]))
                .findFirst();

        if (indexDivergedAt.isEmpty() && sourcePathIsShorterOrEqual) {
            return EMPTY_PATH;
        }

        int relativeElementsStart = indexDivergedAt.isEmpty() ? targetPathLength : indexDivergedAt.getAsInt();

        String[] elementsForDeclaration = Arrays.copyOfRange(
                pathElements, relativeElementsStart, sourcePathLength - 1);

        return new NamespacePath(elementsForDeclaration);
    }

    NamespacePath append(String path) {
        return new NamespacePath(this.toString() + NAMESPACE_DELIMITER + path);
    }
}
