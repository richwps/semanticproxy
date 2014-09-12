/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU cache
 *
 * @author fbensman
 * @param <K>
 * @param <V>
 */
public class LRUCache<K, V> {

    public static final int DEFAULT_MAX_SIZE = 1000;
    private final Map<K, V> map;

    public LRUCache() {
        this(DEFAULT_MAX_SIZE);
    }

    public LRUCache(final int maxSize) {
        this.map = (Map<K, V>) Collections.synchronizedMap(new LinkedHashMap<K, V>(maxSize + 1, .75F, true) {
            

            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxSize;
            }
        });
    }

    public V put(K key, V value) {
        return map.put(key, value);
    }

    public V get(K key) {
        return map.get(key);
    }

    public void clear() {
        map.clear();
    }
}