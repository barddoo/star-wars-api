package com.barddoo.cache;

import java.util.Optional;

public interface CacheBean<T, K> {

    void put(T key, K value);

    Optional<K> get(T key);
}
