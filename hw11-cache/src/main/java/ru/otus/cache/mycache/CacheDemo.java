package ru.otus.cache.mycache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.ref.WeakReference;

public class CacheDemo {
   private static Logger logger = LogManager.getLogger(CacheDemo.class);

   public static void main(String[] args) {
      new CacheDemo().demo();
   }

   private void demo() {
      CacheImpl<Integer, Integer> cache = new CacheImpl<>();
      CacheListener<Integer, Integer> listener =
            (key, value, event) -> logger.info("key: {}, value: {}, event: {}", key, value, event);
      cache.addListener(listener);
      cache.put(1, 1);
      logger.info("GET VALUE FROM CACHE: {}", cache.get(1));
      cache.remove(1);
      logger.info("GET VALUE FROM CACHE: {}", cache.get(1)); // value removed from cache
      cache.removeListener(listener);
   }
}
