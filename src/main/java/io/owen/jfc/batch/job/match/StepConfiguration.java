package io.owen.jfc.batch.job.match;

import io.owen.jfc.common.entity.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Created by owen_q on 2018. 7. 20..
 */
@Configuration
public class StepConfiguration {
    private Logger logger = LoggerFactory.getLogger(StepConfiguration.class);

    private static final String STEP_NAME = "StepName";

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step Step() {
        return stepBuilderFactory.get(STEP_NAME)
                .<ZonedDateTime, Match>chunk(1)
                .reader(sampleItemReader())
                .processor(sampleItemProcessor())
                .writer(sampleItemWriter())
                .build();
    }

    @Bean
    public ItemReader<ZonedDateTime> sampleItemReader() {
        return new DateItemReader();
    }

    @Bean
    public ItemProcessor<ZonedDateTime, Match> sampleItemProcessor() {
        return (matchDate) ->{

            Match match = new Match();
            match.setAttendList(new ArrayList<>());
            match.setMatchDate(matchDate);

            return match;
        };
    }

    @Bean
    public JpaItemWriter<Match> sampleItemWriter() {
        JpaItemWriter<Match> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);

        return writer;
    }
}
