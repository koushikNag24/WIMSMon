package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.DiskHealthStatusConf;
import org.isro.istrac.nsa.inoctf.exception.InternalAggregateMonException;
import org.isro.istrac.nsa.inoctf.utils.Utils;


public class DiskHealthStatus implements  HealthStatus{
    private DiskHealthStatusConf config;
    private  Utils utils;
    final static Logger logger = Logger.getLogger(DiskHealthStatus.class);

    public DiskHealthStatus(@NonNull Utils utils,@NonNull DiskHealthStatusConf config) {
        this.config=config;
        this.utils = utils;
    }

    public void logHealthStatus() throws InternalAggregateMonException {
        logger.info(" Processing Disk Health Status");
        int diskHealth= utils.fetchStatus();
        if(aggregateHealthInfo!=null){
            aggregateHealthInfo.setDiskHealthStatus(diskHealth);
        }else {
            throw new InternalAggregateMonException("Unable to initialize Aggregate Info");
        }


    }

}
