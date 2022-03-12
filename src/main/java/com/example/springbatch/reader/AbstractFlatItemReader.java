package com.example.springbatch.reader;

import com.example.springbatch.domain.Person;
import com.example.springbatch.fieldsetmappers.PersonFieldSetMapper;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.Resource;

public abstract class AbstractFlatItemReader<T> extends FlatFileItemReader<T> {
    @Override
    public void setLinesToSkip(int linesToSkip) {
        if(linesToSkip == 0){
            linesToSkip = 1;
        }

        super.setLinesToSkip(linesToSkip);
    }

    @Override
    public void setLineMapper(LineMapper<T> lineMapper) {
        DefaultLineMapper<T> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(new DelimitedLineTokenizer(";"));
        //defaultLineMapper.setFieldSetMapper(new PersonFieldSetMapper());

        super.setLineMapper(lineMapper);
    }

    @Override
    public void setResource(Resource resource) {
        super.setResource(resource);
    }
}
