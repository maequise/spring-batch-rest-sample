package com.example.springbatch.reader;

import com.example.springbatch.reader.interfaces.ExcelStreamReader;
import org.springframework.batch.item.*;

public abstract class AbstractExcelStreamReader<T> implements ExcelStreamReader<T> {

    public abstract T doRead() throws Exception;

    public abstract void doOpen();

    public abstract void doClose();

    public abstract void doUpdate();

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
       return doRead();
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        doOpen();
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        doUpdate();
    }

    @Override
    public void close() throws ItemStreamException {
        doClose();
    }
}
