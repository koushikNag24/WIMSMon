package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.exception.InternalAggregateMonException;
import org.isro.istrac.nsa.inoctf.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SystemDHealthStatus implements  HealthStatus{
    Utils utils;
    public SystemDHealthStatus(@NonNull Utils utils) {
        this.utils = utils;
    }
    final static Logger logger = Logger.getLogger(SystemDHealthStatus.class);
    public void logHealthStatus() throws InternalAggregateMonException {
        logger.info(" Processing SystemD Health Status");
        int systemDHealth= utils.fetchStatus();
        if(aggregateHealthInfo!=null){
            aggregateHealthInfo.setSystemDHealthStatus(systemDHealth);
           aggregateHealthInfo.setEpoch(String.valueOf(LocalDateTime.now()));

        }else {
            throw new InternalAggregateMonException("Unable to initialize Aggregate Info");
        }

    }
}
