package io.owen.jfc.batch.job.match;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by owen_q on 2018. 7. 20..
 */
@Configuration
@EnableBatchProcessing
public class JobConfiguration {
    private Logger logger = LoggerFactory.getLogger(JobConfiguration.class);

    private static final String JOB_NAME = "match-generate";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private Step step;

    @Bean
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(step)
                .on("COMPLETED")
                .end().build().build();
    }
}
