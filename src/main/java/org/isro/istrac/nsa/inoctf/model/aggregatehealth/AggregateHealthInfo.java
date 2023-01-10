package org.isro.istrac.nsa.inoctf.model.aggregatehealth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.Config;
import org.isro.istrac.nsa.inoctf.exception.ConfigException;
import org.isro.istrac.nsa.inoctf.strategy.LogStrategy;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.isro.istrac.nsa.inoctf.config.ConsoleColors.RED_BOLD_BRIGHT;
import static org.isro.istrac.nsa.inoctf.config.ConsoleColors.RESET;

@Getter
@Setter
@ToString

public class AggregateHealthInfo {
    @ToString.Exclude
    private DateTimeFormatter DATE_TIME_FORMATTER;
    final static Logger logger = Logger.getLogger(AggregateHealthInfo.class);
    private List<BaseAggregateHealthInfo> aggregateHealthInfos;

    private String epoch;
    private Config config;
    public void log(LogStrategy logStrategy){
        logStrategy.log(this,config);
    }

    public void addAggregateHealthInfo(BaseAggregateHealthInfo healthInfo){
        this.aggregateHealthInfos.add(healthInfo);
    }
    public void setDateTimeFormatter( String formatter){
        if(formatter==null){
            try {
                throw  new ConfigException("Invalid Software Configuration");
            } catch (ConfigException e) {
                logger.warn(RED_BOLD_BRIGHT+ " [ Tentative Reason : Configuration File is not in YAML format as expected by the software! ] "+e.getMessage()+RESET);
            }
            return;
        }
        this.DATE_TIME_FORMATTER=DateTimeFormatter.ofPattern(formatter);
    }
    public void setEpoch() {
        try {
           LocalDateTime currentEpoch=LocalDateTime.now(Clock.systemUTC());
           this.epoch = currentEpoch.format(DATE_TIME_FORMATTER);
        }catch (Exception e){
            logger.error("Could not Parse Epoch");

        }
    }
}
