class LockOne implements Lock {
    private final boolean[] flag = new boolean[2];
    // thread-local index, 0 or 1
    public void lock() {
        int i = Math.toIntExact(Thread.currentThread().getId());
        int j = 1-i;
        flag[i] = true;
        while (flag[j]) {}
    }

    public void unlock(){
        int i = Math.toIntExact(Thread.currentThread().getId());
        flag[i] = false;
    }
}