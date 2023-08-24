class Peterson implements Lock {
    private final boolean[] flag = new boolean[2];
    private volatile int victim;
    public void lock() {
        int i = Math.toIntExact(Thread.currentThread().getId());
        int j = 1 - i;
        flag[i] = true;
        victim = i;
        while (flag[j] && victim==i){}
    }

    public void unlock() {
        int i = Math.toIntExact(Thread.currentThread().getId());
        flag[i] = false;
    }
}
