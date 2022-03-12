package com.example.springbatch.fieldsetmappers;

import com.example.springbatch.domain.Person;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class PersonFieldSetMapper implements FieldSetMapper<Person> {
    @Override
    public Person mapFieldSet(FieldSet fieldSet) throws BindException {
        Person person = new Person();
        person.setLastname(fieldSet.readString(0));
        person.setName(fieldSet.readString(1));

        return person;
    }
}
