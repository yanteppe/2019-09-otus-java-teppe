package ru.otus.cache.mycache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.*;

public class CacheImpl<K, V> implements Cache<K, V>, CacheListener<K, V> {
   private static Logger logger = LogManager.getLogger(CacheImpl.class);
   private final Map<K, V> cache;
   private final List<WeakReference<CacheListener<K, V>>> listeners;
   private final ReferenceQueue<CacheListener<K, V>> listenersQueue;

   public CacheImpl() {
      this.cache = new WeakHashMap<>();
      this.listeners = new ArrayList<>();
      this.listenersQueue = new ReferenceQueue<>();
   }

   @Override
   public void put(K key, V value) {
      cache.put(key, value);
      notifyListeners(key, value, String.format("CACHE PUT - key: %s, value: %s", key, value));
   }

   @Override
   public void remove(K key) {
      V value = cache.remove(key);
      notifyListeners(key, value, String.format("CACHE REMOVE - key: %s, value: %s", key, value));
   }

   @Override
   public V get(K key) {
      V value = cache.get(key);
      return value;
   }

   @Override
   public void addListener(CacheListener listener) {
      listeners.add(new WeakReference<CacheListener<K, V>>(listener, listenersQueue));
   }

   @Override
   public void removeListener(CacheListener listener) {
      listeners.remove(listener);
   }

   private void notifyListeners(K key, V value, String event) {
      for (WeakReference<CacheListener<K, V>> listener : listeners) {
         if (listener != null) {
            Objects.requireNonNull(listener.get()).notify(key, value, event);
         }
      }
   }

   @Override
   public void notify(K key, V value, String event) {
      logger.info("LISTENER - key: {}, value: {}, action: {}", key, value, event);
   }
}
