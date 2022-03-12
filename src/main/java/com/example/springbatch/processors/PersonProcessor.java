package com.example.springbatch.processors;

import com.example.springbatch.domain.Person;
import com.example.springbatch.entities.PersonEntity;
import org.springframework.batch.item.ItemProcessor;

public class PersonProcessor implements ItemProcessor<Person, PersonEntity> {
    @Override
    public PersonEntity process(Person item) throws Exception {
        PersonEntity entity = new PersonEntity();
        entity.setName(item.getName());
        entity.setLastname(item.getLastname());

        return entity;
    }
}
