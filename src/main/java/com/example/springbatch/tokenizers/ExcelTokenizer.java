package com.example.springbatch.tokenizers;

import com.example.springbatch.domain.Person;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DefaultFieldSetFactory;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FieldSetFactory;
import org.springframework.batch.item.file.transform.LineTokenizer;

public class ExcelTokenizer implements LineTokenizer {
    private FieldSetMapper<Person> fieldSet;
    private FieldSetFactory fieldSetFactory = new DefaultFieldSetFactory();


    @Override
    public FieldSet tokenize(String line) {
        return this.fieldSetFactory.create(line.split(";"));
        //return null;
    }
}
