package com.h2test.sprngbt.cache;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InStorageCache<K, V >
        implements Cache<K, V> {

    private static final Logger logger =
            LoggerFactory.getLogger(InStorageCache.class);

    private final ConcurrentHashMap<K, String> map;
    private final int cacheMaxSize;
    private final String pathToFile;
    private final String storage = "storage";

    public InStorageCache(int cacheMaxSize, String pathToFile) {
        this.cacheMaxSize = cacheMaxSize;
        this.pathToFile = pathToFile;
        this.map = new ConcurrentHashMap<K, String>();
    }
    @Override
    public V get(K key) {
        if (contains(key)) {
            File file = getFile(key);
            FileInputStream fis = null;
            ObjectInputStream ois = null;
            try {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                return (V) ois.readObject();
            } catch (Exception e) {
                logger.error("can't read file");
            } finally {
                try {
                    ois.close();
                    fis.close();
                } catch (IOException e) {
                    logger.error("can't close" +
                            "input stream");
                }
            }
        }
        return null;
    }
    @Override
    public void put(K key, V value) {
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try {
            File file = createFile();
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(value);
            oos.flush();
            map.put(key, file.getName());
        } catch (IOException e) {
            logger.error("can't create file");

        } finally {
            try {
                oos.close();
                fos.close();
            } catch (IOException e) {
                logger.error("can't close" +
                        "output stream");
            }
        }
    }

    @Override
    public void putIfAbsent(K key, V value) {
        if (!map.contains(key)) {
            put(key, value);
        }
    }
    @Override
    public boolean contains(K key) {
        return map.containsKey(key);
    }
    @Override
    public void remove(K key) {
        File file = getFile(key);
        if (!file.delete()) {
            logger.error("error deleting file");
        }
        map.remove(key);
    }
    @Override
    public int size() {
        return map.size();
    }
    @Override
    public void clear() {
        File dir = new File(storage);

        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {// remove cache files
                if (file.delete()) {
                    logger.error(format(
                            "File '%s' has been deleted", file));
                } else {
                    logger.error(format(
                            "Can't delete a file %s", file));
                }
            }
            map.clear();
        }
    }

    private int createDir(String storage) {
        File dir = new File(storage);
        if (!dir.exists()) {
            if (dir.mkdir())
                return 0;
            else
                return 1;
        } else
            return 0;
    }

    private File createFile() {
        String randomPostfix = UUID.
                randomUUID().
                toString();

        return new File(storage +
                File.separator +
                randomPostfix);
    }

    private File getFile(K key) {
        String fileName = map.get(key);
        return new File(storage +
                File.separator +
                fileName);
    }
    @Override
    public int maxSize(){
        return cacheMaxSize;
    }
}
