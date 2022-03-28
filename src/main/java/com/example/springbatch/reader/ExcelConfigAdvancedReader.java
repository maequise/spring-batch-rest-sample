package com.example.springbatch.reader;

import com.example.springbatch.domain.ConfigInputDto;
import com.example.springbatch.domain.Person;
import com.example.springbatch.linemappers.ExcelConfigLineMapper;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.core.io.PathResource;

public class ExcelConfigAdvancedReader extends AbstractExcelReader<ConfigInputDto> {
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        super.setResource(new PathResource(executionContext.get("file").toString()));
        super.setLineMapper(new ExcelConfigLineMapper());
        super.skipRows(1);
        super.setNameSheet("CONFIG");

        super.open(executionContext);
    }
}
