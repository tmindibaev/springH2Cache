package com.h2test.sprngbt.cache;

public interface Cache<K, V> // key, value
{
    void put(K key, V value);

    void putIfAbsent(K key, V value);

    boolean contains(K key);

    V get(K key);

    void remove(K key);

    void clear();

    int size();

    int maxSize();

}