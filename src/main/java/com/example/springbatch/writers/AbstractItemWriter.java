package com.example.springbatch.writers;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public abstract class AbstractItemWriter<T> implements ItemWriter<T> {
    @Override
    public void write(List<? extends T> items) throws Exception {
        doWrite(items);
    }

    public abstract void doWrite(List<? extends T> items);
}
