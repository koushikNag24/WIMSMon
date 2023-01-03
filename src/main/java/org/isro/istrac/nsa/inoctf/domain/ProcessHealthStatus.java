package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.exception.InternalAggregateMonException;
import org.isro.istrac.nsa.inoctf.utils.Utils;

public class ProcessHealthStatus implements HealthStatus{
    Utils utils;
    public ProcessHealthStatus(@NonNull Utils utils) {
        this.utils = utils;
    }
    final static Logger logger = Logger.getLogger(ProcessHealthStatus.class);
    public void logHealthStatus() throws InternalAggregateMonException {
        logger.info(" Processing Process Health Status");
        int processHealth= utils.fetchStatus();
        if(aggregateHealthInfo!=null){
            aggregateHealthInfo.setProcessHealthStatus(processHealth);
        }else {
            throw new InternalAggregateMonException("Unable to initialize Aggregate Info");
        }

    }
}
