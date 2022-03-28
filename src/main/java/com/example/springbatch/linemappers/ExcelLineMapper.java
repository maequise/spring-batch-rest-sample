package com.example.springbatch.linemappers;

import com.example.springbatch.EndExcelLine;
import com.example.springbatch.domain.Person;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;

public class ExcelLineMapper implements LineMapper<Person> {
    private LineTokenizer lineTokenizer = new DelimitedLineTokenizer(";");

    @Override
    public Person mapLine(String line, int lineNumber) throws Exception {
        FieldSet fieldSet = this.lineTokenizer.tokenize(line);

        Person person = new Person();
        person.setName(fieldSet.readString(0));
        person.setLastname(fieldSet.readString(1));


        return person;
    }
}
