package com.example.springbatch.controllers;

import com.example.springbatch.services.StorageService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@EnableBatchProcessing
@RestController
@RequestMapping("/api/batch")
public class TestController {
    @Autowired
    private SimpleJobLauncher jobLauncher;

    @Autowired
    @Qualifier("jobPerson")
    private Job personJon;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private StorageService storageService;

    @Value("${java.io.tmpdir}")
    private String tmpDir;

    @GetMapping("/test")
    public void test() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException, NoSuchJobException {

        Map<String, JobParameter> valuesParameters = new HashMap<>();

        valuesParameters.put("file", new JobParameter("filepath"));

        JobParameters parameters = new JobParameters(valuesParameters);


        jobLauncher.run(personJon,parameters);


    }

    @PostMapping(value = "/test-post")
    public void testPost(@RequestParam("file") MultipartFile data/*, @RequestParam("file_input") MultipartFile fileInput*/) throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        try {
            OutputStream outputStream = new FileOutputStream(tmpDir + data.getOriginalFilename());

            data.getInputStream().transferTo(outputStream);

            outputStream.flush();
            outputStream.close();

           /* outputStream = new FileOutputStream(tmpDir + fileInput.getOriginalFilename());
            fileInput.getInputStream().transferTo(outputStream);
            outputStream.flush();
            outputStream.close();*/

        }catch (IOException e) {

        }

        Map<String, JobParameter> valuesParameters = new HashMap<>();

        valuesParameters.put("timestamp", new JobParameter(Instant.now().toEpochMilli()));
        valuesParameters.put("file", new JobParameter(tmpDir + data.getOriginalFilename()));
        //valuesParameters.put("file_input", new JobParameter(tmpDir + fileInput.getOriginalFilename()));

        JobParameters parameters = new JobParameters(valuesParameters);
/*

        JobExecution previousJobExecution = jobRepository.getLastJobExecution("personJob", parameters);

        previousJobExecution.setVersion(3);
        previousJobExecution.setId(2L);

        jobRepository.update(previousJobExecution);
*/


        jobLauncher.run(personJon,parameters);

        //storageService.storeFile(data);
    }
}
