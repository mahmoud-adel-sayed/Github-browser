package com.android.example.github.browser;

import android.util.Log;
import android.util.LruCache;

import java.util.List;

import javax.inject.Inject;

public final class MemoryCache {
    private static final String TAG = MemoryCache.class.getSimpleName();
    private static LruCache<String, List<?>> mCache;

    @Inject
    public MemoryCache() {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // Use 1/4th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 4;
        mCache = new LruCache<>(cacheSize);
    }

    public <T> void add(String key, List<T> value) {
        if ((value != null && !value.isEmpty()) && get(key) == null) {
            mCache.put(key, value);
            Log.e(TAG, "Added (" + key + ") into memory cache");
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> get(String key) {
        return (List<T>) mCache.get(key);
    }

    public void clear() {
        mCache.evictAll();
    }

    public void remove(String cacheName) {
        int size = mCache.size();
        String key;

        for (int i = 1; i <= size; i++) {
            key = cacheName + i;
            if (mCache.get(key) != null) mCache.remove(key);
        }
    }

    public void deleteItem(String key) {
        mCache.remove(key);
    }
}
