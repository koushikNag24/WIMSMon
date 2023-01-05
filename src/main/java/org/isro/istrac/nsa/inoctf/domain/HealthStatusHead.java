package org.isro.istrac.nsa.inoctf.domain;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.Config;
import org.isro.istrac.nsa.inoctf.exception.InternalAggregateMonException;
import org.isro.istrac.nsa.inoctf.strategy.FileLogStrategy;

import java.io.IOException;
import java.util.List;
@AllArgsConstructor
public class HealthStatusHead implements HealthStatus{
    Config config;
    final static Logger logger = Logger.getLogger(HealthStatusHead.class);
    private List<HealthStatus> healthStatuses;
    public void addHealthStatus(HealthStatus healthStatus){
        this.healthStatuses.add(healthStatus);
    }
    public void removeHealthStatus(HealthStatus healthStatus){
        this.healthStatuses.remove(healthStatus);
    }
    public void logHealthStatus() {
        for (HealthStatus healthStatus : this.healthStatuses) {
            try {
                try {
                    healthStatus.logHealthStatus();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (InternalAggregateMonException e) {
                throw new RuntimeException(e);
            }
        }
        aggregateHealthInfo.setConfig(config);
        aggregateHealthInfo.setDateTimeFormatter(config.getEpochFormatter());
        aggregateHealthInfo.setEpoch();
        logger.debug(aggregateHealthInfo.toString());
        aggregateHealthInfo.log(new FileLogStrategy());

    }
}
