package ru.otus.cache.mycache;

public interface Cache<K, V> {

   void put(K key, V value);

   void remove(K key);

   V get(K key);

   void addListener(CacheListener listener);

   void removeListener(CacheListener listener);

}
