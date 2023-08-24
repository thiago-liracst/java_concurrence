class Counter {
    private long value;
    private Lock lock;

    public Counter(int c){
        value = c;
    }

    public long getAndIncrement() {
        lock.lock();
        long temp = value;
        try {
            value = temp + 1;    
        } finally {
            lock.unlock();
        }
        return temp;
    }
}