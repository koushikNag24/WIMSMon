package org.isro.istrac.nsa.inoctf.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.strategy.LogStrategy;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.ZoneOffset.UTC;

@Getter
@Setter
@ToString

public class AggregateHealthInfo {
    @ToString.Exclude
    private DateTimeFormatter DATE_TIME_FORMATTER;
    final static Logger logger = Logger.getLogger(AggregateHealthInfo.class);
    private int diskHealthStatus;
    private int fileHealthStatus;
    private int processHealthStatus;
    private int systemDHealthStatus;
    private String epoch;
    public void log(LogStrategy logStrategy){
        logStrategy.log(this);
    }
    public void setDateTimeFormatter(String formatter){
        this.DATE_TIME_FORMATTER=DateTimeFormatter.ofPattern(formatter);
    }
    public void setEpoch() {
        try {
           LocalDateTime currentEpoch=LocalDateTime.now(Clock.systemUTC());
           String formattedCurrentEpoch=currentEpoch.format(DATE_TIME_FORMATTER);
            this.epoch = formattedCurrentEpoch;
        }catch (Exception e){
            logger.error("Could not Parse Epoch");
            System.out.println(e.getCause());
        }
    }
}
