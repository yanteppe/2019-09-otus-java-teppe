package ru.otus.cache.mycache;

public interface CacheListener<K, V> {

   void notify(K key, V value, String event);
}
