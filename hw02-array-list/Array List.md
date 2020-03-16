**Лекция №4. Контейнеры и алгоритмы.**

**Цели занятия**
- Познакомиться с Generic-ами в Java и со стандартными коллекциями

**Краткое содержание**
- Generics
- Стандартные коллекции JDK

**Результаты**
- Понимание Generic-ков.
- Понимание особенностей использования стандартных коллекций.

---

**Задание №2. DIY ArrayList.**

Написать свою реализацию ArrayList на основе массива.<br>
Например: `class DIYarrayList<T> implements List<T>{...}`

Проверить, что на ней работают методы из `java.util.Collections`:
  - `Collections.addAll(Collection<? super T> c, T... elements)`
  - `Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)`
  - `Collections.static <T> void sort(List<T> list, Comparator<? super T> c)`

1. Проверить на коллекциях с 20 и более элементами.
2. DIYarrayList должен имплементировать **только один** интерфейс - _List_.
3. Если метод не имплементирован, то он должен выбрасывать исключение _UnsupportedOperationException_.

**Решение**
- Реализованы все требования задачи.
- Проверка через автотесты Junit.