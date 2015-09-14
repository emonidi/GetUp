package com.example.emonidi.getup;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by emonidi on 13.9.2015 г..
 */
public class LastStepQue implements BlockingQueue<Long> {

    @Override
    public boolean add(Long aLong) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Long> collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean offer(Long aLong) {
        return false;
    }

    @Override
    public Long remove() {
        return null;
    }

    @Override
    public Long poll() {
        return null;
    }

    @Override
    public Long element() {
        return null;
    }

    @Override
    public Long peek() {
        return null;
    }

    @Override
    public void put(Long aLong) throws InterruptedException {

    }

    @Override
    public boolean offer(Long aLong, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Long take() throws InterruptedException {
        return null;
    }

    @Override
    public Long poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public int size() {
        return 1;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] array) {
        return null;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @NonNull
    @Override
    public Iterator<Long> iterator() {
        return null;
    }

    @Override
    public int drainTo(Collection<? super Long> c) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super Long> c, int maxElements) {
        return 0;
    }
}
