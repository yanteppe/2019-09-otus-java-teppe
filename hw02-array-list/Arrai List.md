**Задание №2. DIY ArrayList.**

Написать свою реализацию ArrayList на основе массива.
class DIYarrayList<T> implements List<T>{...}

Проверить, что на ней работают методы из `java.util.Collections`:
  - `Collections.addAll(Collection<? super T> c, T... elements)`
  - `Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)`
  - `Collections.static <T> void sort(List<T> list, Comparator<? super T> c)`

1. Проверить на коллекциях с 20 и более элементами.
2. DIYarrayList должен имплементировать **только один** интерфейс - List..
3. Если метод не имплементирован, то он должен выбрасывать исключение UnsupportedOperationException.

**Решение**

Реализованы все требования задачи.
Проверка через автотесты Junit .