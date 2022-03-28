package com.example.springbatch.reader;

import com.example.springbatch.domain.Person;
import com.example.springbatch.linemappers.ExcelLineMapper;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;

public class ExcelAdvancedReader extends AbstractExcelReader<Person> {
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        super.setResource(new PathResource(executionContext.get("file").toString()));
        super.setLineMapper(new ExcelLineMapper());
        super.open(executionContext);
    }
}
