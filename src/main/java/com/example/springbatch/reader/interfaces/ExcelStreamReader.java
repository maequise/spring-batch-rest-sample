package com.example.springbatch.reader.interfaces;

import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamReader;

public interface ExcelStreamReader<T> extends ItemStreamReader<T>{
    default void closeResource() {

    }

    default void closeWorkbook() {

    }
}
