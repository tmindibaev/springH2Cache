package com.h2test.sprngbt.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
    private static final Logger logger =
            LoggerFactory.getLogger(InStorageCache.class);
    public static void main(String[] args) {
        Cache<String, String> cache = new CacheBuilder()
                .setMaxInMemorySize(10)
                .setMaxInStorageSize(100)
                .setPathToFile("dir")
                .build();

        cache.put("1", "a");
        System.out.println(cache.get("1"));
        cache.putIfAbsent("1","b");
        System.out.println(cache.get("1"));
        cache.putIfAbsent("1","c");
        System.out.println(cache.get("1"));

        cache.putIfAbsent("2","c");
        System.out.println(cache.get("2"));
    }
}
