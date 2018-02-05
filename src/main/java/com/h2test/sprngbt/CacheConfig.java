package com.h2test.sprngbt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cache")
public class CacheConfig {
    private InMemory inmemory;
    private InStorage instorage;

    public InMemory getInmemory() {
        return inmemory;
    }

    public void setInmemory(InMemory inmemory) {
        this.inmemory = inmemory;
    }

    public InStorage getInstorage() {
        return instorage;
    }

    public void setInstorage(InStorage instorage) {
        this.instorage = instorage;
    }

    public static class InMemory{
        private  int size;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }


    public static class InStorage{
        private  int size;
        private  String dir;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }
    }
}
