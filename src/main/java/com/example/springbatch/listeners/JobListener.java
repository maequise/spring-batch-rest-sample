package com.example.springbatch.listeners;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("test");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        jobExecution.setStatus(BatchStatus.COMPLETED);
    }
}
