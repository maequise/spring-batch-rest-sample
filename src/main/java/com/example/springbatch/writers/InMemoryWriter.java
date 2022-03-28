package com.example.springbatch.writers;

import com.example.springbatch.domain.ConfigInputDto;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;

import java.util.List;

public class InMemoryWriter implements ItemWriter<ConfigInputDto> {
    @Autowired
    public Cache cache;

    @Override
    public void write(List<? extends ConfigInputDto> items) throws Exception {

    }
}
