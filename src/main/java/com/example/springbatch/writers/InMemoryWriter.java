package com.example.springbatch.writers;

import com.example.springbatch.domain.ConfigInputDto;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;

import java.util.List;

public class InMemoryWriter implements ItemWriter<ConfigInputDto> {
    public Cache cache;

    public InMemoryWriter(Cache cache){
        this.cache = cache;
    }

    @Override
    public void write(List<? extends ConfigInputDto> items) throws Exception {
        items.forEach(item -> {
            cache.put(item.getNameProperty(), item.getValueProperty());
        });

        System.out.println("trest");
    }
}
