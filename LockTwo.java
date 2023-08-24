class LockTwo implements Lock {
    private volatile int victim;

    public void lock(){
        int i = Math.toIntExact(Thread.currentThread().getId());
        victim = i;
        while (victim == i) {}
    }

    public void unlock(){}
}
