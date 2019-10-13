package ru.otus;

import java.util.*;

/**
 * Home work №2. DIY ArrayList.<br>
 * Own ArrayList implementation
 *
 * @param <T> Parameterized data type
 */
public class DIYArrayList<T> implements List<T> {
    private T[] array;
    private int idx = 0;

    public DIYArrayList() {
        array = (T[]) new Object[10];
    }

    // region Implemented methods
    @Override
    public int size() {
        return idx;
    }

    /**
     * Add element by last index
     *
     * @param element T element to add
     */
    @Override
    public boolean add(T element) {
        if (checkArrayCapacity(array)) {
            increaseArraySize();
        }
        array[idx] = element;
        idx++;
        return true;
    }

    /**
     * Inserting element into DIYArrayList at index.<br>
     * Shifts all array after inserted
     *
     * @param index   index to add element
     * @param element element to add
     */
    @Override
    public void add(int index, T element) {
        T[] tempArr = (T[]) new Object[array.length + 1];
        System.arraycopy(array, 0, tempArr, 0, index);
        tempArr[index] = element;
        System.arraycopy(array, index, tempArr, index + 1, array.length - index);
        array = tempArr;
        idx++;
    }

    /**
     * Check array capacity
     *
     * @param array array
     * @return boolean
     */
    private boolean checkArrayCapacity(T[] array) {
        return array.length == idx;
    }

    /**
     * Increase array size<br>
     * Formula of increase array: (array.length * 3) / 2 + 1
     */
    private void increaseArraySize() {
        T[] tempArray = array;
        array = (T[]) new Object[(tempArray.length * 3) / 2 + 1];
        System.arraycopy(tempArray, 0, array, 0, tempArray.length);
    }

    /**
     * Adds a new list to the end of the list.<br>
     * If addAll is successful return true, else - false
     *
     * @param c list to add
     * @return boolean
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        T[] temporary = array;
        if (c.size() + idx > array.length) {
            array = (T[]) new Object[temporary.length + c.size()];
        }
        System.arraycopy(temporary, 0, array, 0, temporary.length);
        System.arraycopy(c.toArray(), 0, array, idx, c.size());
        idx = idx + c.size();
        return idx + c.size() == idx + c.size();
    }

    /**
     * Replace element by index
     *
     * @param index   index of element
     * @param element element for replace
     * @return replaced T element
     */
    @Override
    public T set(int index, T element) {
        return array[index] = element;
    }

    /**
     * Removing element by index and return it
     *
     * @param index removed element index
     * @return removed element
     */
    @Override
    public T remove(int index) {
        // Create new array with new size
        T[] temp = array;
        array = (T[]) new Object[array.length - 1];
        // Filling array after deleting element
        T removedElement = null;
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (index > array.length) { // If index out of bound of list
                array = temp;
                throw new IndexOutOfBoundsException(String.format("Index %s out of bounds for length %s", index, array.length));
            }
            if (i != 0) j++;
            if (i == index) {
                removedElement = array[i];
                j++; // Shift after deleted element (skip deleted element index)
            }
            array[i] = temp[j]; // New array after removing element
        }
        --idx;
        return removedElement;
    }

    /**
     * Return element by index
     *
     * @param index index of element
     * @return T element
     */
    @Override
    public T get(int index) {
        return array[index];
    }

    /**
     * Clear list - remove all array
     */
    @Override
    public void clear() {
        array = (T[]) new Object[0];
        idx = array.length;
    }

    /**
     * Check empty list or not<br>
     * If list is empty return true, else return false
     *
     * @return boolean
     */
    @Override
    public boolean isEmpty() {
        return array.length <= 0;
    }

    /**
     * Check element contains in list<br>
     * If contains return true, else false
     *
     * @param object desired object
     * @return boolean
     */
    @Override
    public boolean contains(Object object) {
        for (T element : array) {
            if (element != null) {
                if (element.equals(object)) {
                    return true;
                }
            } else break;
        }
        return false;
    }

    /**
     * Iterating collection array
     *
     * @return Iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            /**
             * Checks if the collection contains the next element or not
             *
             * @return boolean
             */
            @Override
            public boolean hasNext() {
                return idx < array.length;
            }

            /**
             * Returns the next element the collection<br>
             * If there is no next item in the collection, returns null
             *
             * @return element of collection or null;
             */
            @Override
            public T next() {
                if (hasNext()) {
                    return array[idx++];
                } else return null;
            }
        };
    }

    /**
     * Returns a list iterator over the array in this list (in proper
     * sequence).
     *
     * @return a list iterator over the array in this list (in proper
     * sequence)
     */
    @Override
    public ListIterator<T> listIterator() {
        return new ListIterator<T>() {
            @Override
            public boolean hasNext() {
                return iterator().hasNext();
            }

            @Override
            public T next() {
                return iterator().next();
            }

            @Override
            public boolean hasPrevious() {
                return idx > array.length;
            }

            @Override
            public T previous() {
                return array[idx--];
            }

            @Override
            public int nextIndex() {
                return idx++;
            }

            @Override
            public int previousIndex() {
                return idx--;
            }

            @Deprecated
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Deprecated
            @Override
            public void set(T t) {
            }

            @Deprecated
            @Override
            public void add(T t) {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Returns an array of array in current collection.<br>
     * Required to use Collections.sort (...)
     *
     * @return array of array collections
     */
    @Override
    public Object[] toArray() {
        return array;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof DIYArrayList)) return false;
        DIYArrayList<?> that = (DIYArrayList<?>) object;
        return idx == that.idx && Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(idx);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    // endregion


    // region Not implemented methods
    @Deprecated
    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
    // endregion
}
