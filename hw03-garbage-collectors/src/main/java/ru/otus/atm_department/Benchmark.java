package ru.otus.atm_department;

/**
 * Benchmark for checking garbage collectors
 */
class Benchmark implements BenchmarkMBean {
    private final int counter;
    private volatile int size = 0;

    public Benchmark(int loopCounter) {
        this.counter = loopCounter;
    }

    void run() throws InterruptedException {
        for (int idx = 0; idx < counter; idx++) {
            int local = size;
            Object[] array = new Object[local];
            for (int i = 0; i < local; i++) {
                array[i] = new String(new char[0]);
            }
            Thread.sleep(10); //Label_1
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        System.out.println("new size: " + size);
        System.out.println();
        this.size = size;
    }
}
