package com.example.springbatch.linemappers;

import com.example.springbatch.domain.ConfigInputDto;
import com.example.springbatch.domain.Person;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;

public class ExcelConfigLineMapper implements LineMapper<ConfigInputDto> {
    private LineTokenizer lineTokenizer = new DelimitedLineTokenizer(";");

    @Override
    public ConfigInputDto mapLine(String line, int lineNumber) throws Exception {
        ConfigInputDto config = new ConfigInputDto();
        FieldSet fieldSet = this.lineTokenizer.tokenize(line);

        config.setNameProperty(fieldSet.readString(0));
        config.setValueProperty(fieldSet.readString(1));

        return config;
    }
}
