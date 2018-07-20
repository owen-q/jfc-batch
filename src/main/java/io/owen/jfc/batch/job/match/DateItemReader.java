package io.owen.jfc.batch.job.match;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by owen_q on 2018. 7. 20..
 */
public class DateItemReader implements ItemReader {
    private Logger logger = LoggerFactory.getLogger(DateItemReader.class);
    private int count=0;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if(count >= 1){
            return null;
        }
        else{
            final String ZONE_ID = "Asia/Seoul";

            ZonedDateTime today = ZonedDateTime.now(ZoneId.of(ZONE_ID));
            ZonedDateTime matchDate = ZonedDateTime.now(ZoneId.of(ZONE_ID));

            DayOfWeek todayDayOfWeek = today.getDayOfWeek();

            matchDate = matchDate.plusDays(DayOfWeek.SUNDAY.minus(todayDayOfWeek.ordinal()).ordinal()).withHour(10);

            count++;
            return matchDate;
        }
    }
}
