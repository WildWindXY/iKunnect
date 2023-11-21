package utils;

import java.io.Serializable;

public record Triple<A, B, C>(A first, B second, C third) implements Serializable {
}