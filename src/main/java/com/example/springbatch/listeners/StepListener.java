package com.example.springbatch.listeners;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class StepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        String file = stepExecution.getJobExecution().getJobParameters().getParameters().get("file").toString();

        stepExecution.getExecutionContext().put("file", file);
        System.out.println("test");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("test");
        return null;
    }
}
