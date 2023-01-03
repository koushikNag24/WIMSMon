package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.exception.InternalAggregateMonException;
import org.isro.istrac.nsa.inoctf.utils.Utils;

public class FileHealthStatus implements HealthStatus{

    final static Logger logger = Logger.getLogger(FileHealthStatus.class);
    Utils utils;
    public FileHealthStatus(@NonNull Utils utils) {
        this.utils = utils;
    }
    public void logHealthStatus() throws InternalAggregateMonException {
        logger.info(" Processing File Health Status");
        int diskHealth= utils.fetchStatus();
        if(aggregateHealthInfo!=null){
            aggregateHealthInfo.setFileHealthStatus(diskHealth);
        }else {
            throw new InternalAggregateMonException("Unable to initialize Aggregate Info");
        }

    }
}
