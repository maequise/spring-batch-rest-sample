package com.example.springbatch.configuration;

import com.example.springbatch.EndExcelLine;
import com.example.springbatch.domain.ConfigInputDto;
import com.example.springbatch.domain.Person;
import com.example.springbatch.entities.PersonEntity;
import com.example.springbatch.listeners.JobListener;
import com.example.springbatch.listeners.StepListener;
import com.example.springbatch.processors.PersonProcessor;
import com.example.springbatch.reader.ExcelAdvancedReader;
import com.example.springbatch.reader.ExcelConfigAdvancedReader;
import com.example.springbatch.reader.ExcelReader;
import com.example.springbatch.reader.PersonReader;
import com.example.springbatch.repositories.PersonRepository;
import com.example.springbatch.services.StorageService;
import com.example.springbatch.services.StorageServiceImpl;
import com.example.springbatch.tasklets.TempFileTasklet;
import com.example.springbatch.writers.InMemoryWriter;
import com.example.springbatch.writers.PersonWriter;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonBatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private Cache cache;

    @Bean
    public StorageService storageService() {
        return new StorageServiceImpl();
    }

    @Bean(name = "jobPerson")
    public Job personJob(PersonRepository personRepository) {
        return jobBuilderFactory.get("personJob")
                .incrementer(new RunIdIncrementer())
                .flow(configStep())
                .next(personStep1(personRepository))
                .end()
                .listener(new JobListener())
                .build();
    }

    @Bean
    public Step configStep() {
        return stepBuilderFactory.get("config")
                .listener(new StepListener())
                .<ConfigInputDto, ConfigInputDto>chunk(3)
                .reader(new ExcelConfigAdvancedReader())
                .writer(new InMemoryWriter(cache))
                .build();
    }

   /* @Bean
    public Step personStep1(PersonRepository personRepository) {
        return stepBuilderFactory.get("person_step1")
                .listener(new StepListener())
                .<Person, PersonEntity> chunk(5)
                .reader(new PersonReader(storageService()))
                .processor(new PersonProcessor())
                .writer(new PersonWriter(personRepository))
                .build();
    }*/

    @Bean
    public Step personStep1(PersonRepository personRepository) {
        return stepBuilderFactory.get("person_step1")
                .listener(new StepListener())
                .<Person, PersonEntity> chunk(5)
                .reader(new ExcelAdvancedReader(cache))
                .processor(new PersonProcessor())
                .writer(new PersonWriter(personRepository))
                .faultTolerant()
                .skip(EndExcelLine.class)
                .skipLimit(10)
                .build();
    }
}
