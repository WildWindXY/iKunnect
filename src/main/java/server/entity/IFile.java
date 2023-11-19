package server.entity;

/**
 * The IFile interface defines the common behavior for file-related operations.
 *
 * @param <T> The type of the implementing class.
 */
public interface IFile<T extends IFile<T>> {
    /**
     * Gets the path of the file.
     *
     * @return The path of the file.
     */
    String getPath();
}
