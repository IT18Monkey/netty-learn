package com.github.it18monkey.current;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap(5);
        concurrentHashMap.put("1","1");
        concurrentHashMap.put("2","1");
        concurrentHashMap.put("3","1");
        concurrentHashMap.put("4","1");
        concurrentHashMap.put("5","1");
    }
}
