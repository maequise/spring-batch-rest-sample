package com.example.springbatch.listeners;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class StepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        String fileInput = stepExecution.getJobExecution().getJobParameters().getString("file");
        //String fileInput = stepExecution.getJobExecution().getJobParameters().getString("file_input");


        stepExecution.getExecutionContext().put("file", fileInput);
        /*stepExecution.getExecutionContext().put("file_input", fileInput);

        if(fileConfig.contains("config")){
            stepExecution.getExecutionContext().put("isConfig", true);
        }*/
        System.out.println("test");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("test");
        stepExecution.getExecutionContext().clearDirtyFlag();
        return ExitStatus.COMPLETED;
    }
}
