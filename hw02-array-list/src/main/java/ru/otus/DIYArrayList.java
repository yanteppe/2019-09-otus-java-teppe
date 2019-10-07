package ru.otus;

import java.util.*;

/**
 * Home work №2. DIY ArrayList.<br>
 * Own ArrayList implementation
 *
 * @param <T> Parameterized data type
 */
public class DIYArrayList<T> implements List<T> {
    private final int DEFAULT_SIZE = 10;
    private T[] elements;
    private int elementIndex = 0;

    // TODO: подумать над "Unchecked cast: 'java.lang.Object[]' to 'T[]'" во свем классе...
    public DIYArrayList() {
        try {
            elements = (T[]) new Object[0];
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }

    // region Implemented methods
    @Override
    public int size() {
        return elements.length;
    }

    /**
     * Add element in DIYArrayList at last index
     *
     * @param element T element to add
     */
    @Override
    public boolean add(T element) {
        try {
            T[] temporary = elements;
            elements = (T[]) new Object[temporary.length + 1];
            System.arraycopy(temporary, 0, elements, 0, temporary.length);
            elements[elements.length - 1] = element;
            return true;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * Inserting element into DIYArrayList at index.<br>
     * Shifts all elements after inserted
     *
     * @param index   index to add element
     * @param element element to add
     */
    @Override
    public void add(int index, T element) {
        T[] temporary = elements;
        try {
            elements = (T[]) new Object[temporary.length + 1];
            int j = 0;
            for (int i = 0; i < elements.length; i++) {
                if (i != 0) j++;
                if (i == index) {          // If element index in list equals index new element - insert new element
                    elements[i] = element; // Insert new element
                    i++;                   // Shift after add element by insert in list
                }
                elements[i] = temporary[j];
            }
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }

    // TODO: не реализован, реализовать позже
    @Override
    public boolean addAll(Collection<? extends T> c) throws UnsupportedOperationException {
//        T[] temporary = (T[]) new Object[elements.length + c.size()];
//        System.arraycopy(elements, 0, temporary, 0, elements.length);
        return false;
    }

    // TODO: не реализован, реализовать позже
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
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
        return elements[index] = element;
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
        T[] temp = elements;
        try {
            elements = (T[]) new Object[elements.length - 1];
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
        // Filling array after deleting element
        T removedElement = null;
        int j = 0;
        for (int i = 0; i < elements.length; i++) {
            if (index > elements.length) { // If index out of bound of list
                elements = temp;
                throw new IndexOutOfBoundsException(String.format("Index %s out of bounds for length %s", index, elements.length));
            }
            if (i != 0) j++;
            if (i == index) {
                removedElement = elements[i];
                j++; // Shift after deleted element (skip deleted element index)
            }
            elements[i] = temp[j]; // New array after removing element
        }
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
        return elements[index];
    }

    /**
     * Clear list - remove all elements
     */
    @Override
    public void clear() {
        try {
            elements = (T[]) new Object[0];
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Check empty list or not<br>
     * If list is empty return true, else return false
     *
     * @return boolean
     */
    @Override
    public boolean isEmpty() {
        return elements.length <= 0;
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
        for (T element : elements) {
            if (element.equals(object)) return true;
        }
        return false;
    }

    /**
     * Iterating collection elements
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
                return elementIndex < elements.length;
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
                    return elements[elementIndex++];
                } else return null; // TODO: подумать правильно ли возвращать null...
            }
        };
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence).
     *
     * @return a list iterator over the elements in this list (in proper
     * sequence)
     */
    @Override
    public ListIterator<T> listIterator() { // TODO: позже добавить JavaDoc для методов...
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
                return elementIndex > elements.length;
            }

            @Override
            public T previous() {
                return elements[elementIndex--];
            }

            @Override
            public int nextIndex() {
                return elementIndex++;
            }

            @Override
            public int previousIndex() {
                return elementIndex--;
            }

            @Override
            public void remove() throws UnsupportedOperationException {
            }

            @Override
            public void set(T t) throws UnsupportedOperationException {
            }

            @Override
            public void add(T t) throws UnsupportedOperationException {
            }
        };
    }

    /**
     * Returns an array of elements in current collection.<br>
     * Required to use Collections.sort (...)
     *
     * @return array of elements collections
     */
    @Override
    public Object[] toArray() {
        return elements;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof DIYArrayList)) return false;
        DIYArrayList<?> that = (DIYArrayList<?>) object;
        return elementIndex == that.elementIndex && Arrays.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(elementIndex);
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }

    @Override
    public String toString() {
        return "MyArrayList{" +
                "elements=" + Arrays.toString(elements) +
                ", length=" + elements.length +
                '}';
    }
    // endregion

    // region Not implemented methods
    @Override
    public <T1> T1[] toArray(T1[] a) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) throws UnsupportedOperationException {
        return false;
    }

    @Override
    public boolean remove(Object object) throws UnsupportedOperationException {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) throws UnsupportedOperationException {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) throws UnsupportedOperationException {
        return false;
    }

    @Override
    public int indexOf(Object o) throws UnsupportedOperationException {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) throws UnsupportedOperationException {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator(int index) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) throws UnsupportedOperationException {
        return null;
    }
    // endregion
}
