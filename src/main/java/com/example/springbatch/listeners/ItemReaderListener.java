package com.example.springbatch.listeners;

import org.springframework.batch.core.ItemReadListener;

public class ItemReaderListener<T> implements ItemReadListener<T> {
    @Override
    public void beforeRead() {
        System.out.println("test");
    }

    @Override
    public void afterRead(T item) {

    }

    @Override
    public void onReadError(Exception ex) {

    }
}
