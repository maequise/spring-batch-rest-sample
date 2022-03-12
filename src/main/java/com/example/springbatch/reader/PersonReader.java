package com.example.springbatch.reader;

import com.example.springbatch.domain.Person;
import com.example.springbatch.fieldsetmappers.PersonFieldSetMapper;
import com.example.springbatch.services.StorageService;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;

public class PersonReader extends AbstractFlatItemReader<Person> implements StepExecutionListener {
   private StorageService storageService;

    public PersonReader() {

    }

    public PersonReader(StorageService storageService){
        this.storageService = storageService;
    }

    @Override
    public void setLinesToSkip(int linesToSkip) {
        super.setLinesToSkip(linesToSkip);
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        Resource resource = new PathResource((String) executionContext.get("file"));

        setLinesToSkip(1);
        setResource(resource);

        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
        lineMapper.setFieldSetMapper(new PersonFieldSetMapper());
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer(";"));

        super.setLineMapper(lineMapper);

        super.open(executionContext);
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("test");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
