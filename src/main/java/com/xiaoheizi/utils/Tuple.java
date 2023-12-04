package com.xiaoheizi.utils;

import java.io.Serializable;

public record Tuple<A, B>(A first, B second) implements Serializable {
}