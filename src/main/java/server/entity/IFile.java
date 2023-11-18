package server.entity;

public interface IFile<T extends IFile<T>> {
    String getPath();
}
