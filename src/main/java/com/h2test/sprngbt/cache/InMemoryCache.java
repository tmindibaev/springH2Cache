package com.h2test.sprngbt.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryCache<K, V>
        implements Cache<K, V> {

    private static final Logger logger =
            LoggerFactory.getLogger(InStorageCache.class);

    private final Map<K, V> map;
    private final int cacheMaxSize;

    public InMemoryCache(int cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
        this.map = new ConcurrentHashMap();
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public void putIfAbsent(K key, V value) {
        if (!contains(key)) {
            put(key, value);
        }
    }

    @Override
    public boolean contains(K key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public int maxSize() {
        return cacheMaxSize;
    }

    @Override
    public void clear() {
        map.clear();
    }
}