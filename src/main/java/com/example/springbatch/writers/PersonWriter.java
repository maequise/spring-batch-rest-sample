package com.example.springbatch.writers;

import com.example.springbatch.entities.PersonEntity;
import com.example.springbatch.repositories.PersonRepository;

import java.util.List;

public class PersonWriter extends AbstractItemWriter<PersonEntity> {

    private PersonRepository personRepository;

    public PersonWriter() {

    }

    public PersonWriter(PersonRepository repository) {
        this.personRepository = repository;
    }

    @Override
    public void doWrite(List<? extends PersonEntity> items) {

        //items.forEach(item -> personRepository.save(item));
        personRepository.saveAll(items);

        System.out.println("WRITE MODE !!!!!");
    }
}
