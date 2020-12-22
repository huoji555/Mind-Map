package com.hwj.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;

public class KeyLock<K> {
    // 保存所有锁定的KEY及其信号量
    private final ConcurrentMap<K, Semaphore> map = new ConcurrentHashMap<K, Semaphore>();
    // 保存每个线程锁定的KEY及其锁定计数
    private final ThreadLocal<Map<K, LockInfo>> local = new ThreadLocal<Map<K, LockInfo>>() {
        @Override
        protected Map<K, LockInfo> initialValue() {
            return new HashMap<K, LockInfo>();
        }
    };

    /**
     * 锁定key，其他等待此key的线程将进入等待，直到调用{@link #unlock(K)}
     * 使用hashcode和equals来判断key是否相同，因此key必须实现{@link #hashCode()}和
     * {@link #equals(Object)}方法
     *
     * @param key
     */
    public void lock(K key) {
        if (key == null)
            return;
        LockInfo info = local.get().get(key);
        if (info == null) {
            Semaphore current = new Semaphore(1);
            current.acquireUninterruptibly();
            Semaphore previous = map.put(key, current);
            if (previous != null)
                previous.acquireUninterruptibly();
            local.get().put(key, new LockInfo(current));
        } else {
            info.lockCount++;
        }
    }

    /**
     * 释放key，唤醒其他等待此key的线程
     * @param key
     */
    public void unlock(K key) {
        if (key == null)
            return;
        LockInfo info = local.get().get(key);
        if (info != null && --info.lockCount == 0) {
            info.current.release();
            map.remove(key, info.current);
            local.get().remove(key);
        }
    }

    /**
     * 锁定多个key
     * 建议在调用此方法前先对keys进行排序，使用相同的锁定顺序，防止死锁发生
     * @param keys
     */
    public void lock(K[] keys) {
        if (keys == null)
            return;
        for (K key : keys) {
            lock(key);
        }
    }

    /**
     * 释放多个key
     * @param keys
     */
    public void unlock(K[] keys) {
        if (keys == null)
            return;
        for (K key : keys) {
            unlock(key);
        }
    }

    private static class LockInfo {
        private final Semaphore current;
        private int lockCount;

        private LockInfo(Semaphore current) {
            this.current = current;
            this.lockCount = 1;
        }
    }
}

