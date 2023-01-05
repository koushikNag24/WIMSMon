package org.isro.istrac.nsa.inoctf.domain.aggregatehealth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.strategy.LogStrategy;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@ToString

public class AggregateHealthInfo {
    @ToString.Exclude
    private DateTimeFormatter DATE_TIME_FORMATTER;
    final static Logger logger = Logger.getLogger(AggregateHealthInfo.class);
    private int diskHealthStatus;
    private int fileHealthStatus;
    private List<ProcessAggregateHealthInfo> processAggregateHealthInfos;
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
