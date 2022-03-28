package com.example.springbatch.reader;

import com.example.springbatch.domain.Person;
import com.example.springbatch.linemappers.ExcelLineMapper;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.cache.Cache;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;

public class ExcelAdvancedReader extends AbstractExcelReader<Person> {
    private Cache cache;

    public ExcelAdvancedReader(Cache cache){
        this.cache = cache;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        super.setResource(new PathResource(executionContext.get("file").toString()));
        super.setLineMapper(new ExcelLineMapper());
        //@TODO format the number
        super.skipRows(Integer.valueOf(String.valueOf(cache.get("skipRows").get()).replace(".", "")));

        super.setNameSheet(cache.get("to_integrate", String.class));

        super.open(executionContext);
    }
}
