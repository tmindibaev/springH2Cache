package com.h2test.sprngbt.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheMaster<K, V>
        implements Cache<K, V> {
    private static final Logger logger =
            LoggerFactory.getLogger(InStorageCache.class);

    private InMemoryCache<K, V> memoryCache;
    private InStorageCache<K, V> storageCache;

    public CacheMaster(InMemoryCache<K, V> memoryCache, InStorageCache<K, V> storageCache) {
        this.memoryCache = memoryCache;
        this.storageCache = storageCache;
    }

    @Override
    public V get(K key) {
        V value = null;
        if (memoryCache.contains(key))
            value = memoryCache.get(key);
        else if (storageCache.contains(key))
            value = storageCache.get(key);
        return value;
    }

    @Override
    public void put(K key, V value) {
        if (memoryCache.size() < memoryCache.maxSize() ||
                memoryCache.contains(key)) {
            logger.debug(String.format(
                    "Put key %s to memory", key));
            memoryCache.put(key, value);

            if (storageCache.contains(key))
                storageCache.remove(key);
        } else if (memoryCache.size() < storageCache.maxSize() ||
                storageCache.contains(key)) {
            logger.debug(String.format("Put key %s to storage", key));
            storageCache.put(key, value);
        }
    }

    @Override
    public boolean contains(K key) {
        return memoryCache.contains(key) || storageCache.contains(key);
    }

    @Override
    public void remove(K key) {
        if (memoryCache.contains(key))
            memoryCache.remove(key);

        if (storageCache.contains(key))
            storageCache.remove(key);
    }

    @Override
    public int size() {
        return memoryCache.size() +
                storageCache.size();
    }

    @Override
    public void clear() {
        memoryCache.clear();
        storageCache.clear();
    }

    @Override
    public void putIfAbsent(K key, V value) {
        if (!contains(key)) {
            put(key, value);
        }
    }

    @Override
    public int maxSize() {
        return memoryCache.maxSize() + storageCache.maxSize();
    }
}



