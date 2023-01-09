package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.Config;
import org.isro.istrac.nsa.inoctf.config.DiskHealthStatusConf;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.AggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.BaseAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.DiskAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.exception.OsCommandExecException;
import org.isro.istrac.nsa.inoctf.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DiskHealthStatus implements  HealthStatus{
    public static final int DEFAULT_PRIORITY = 10;
    private final Config config;
    private final Utils utils;
    final static Logger logger = Logger.getLogger(DiskHealthStatus.class);

    AggregateHealthInfo aggregateHealthInfo;
    public DiskHealthStatus(@NonNull Utils utils, @NonNull Config config, @NonNull AggregateHealthInfo aggregateHealthInfo) {
        this.config=config;
        this.aggregateHealthInfo=aggregateHealthInfo;
        this.utils = utils;
    }

    public void logHealthStatus() {
        List<String> diskCommandResult = new ArrayList<>();
        DiskHealthStatusConf diskHealthStatusConf=config.getDiskHealthStatusConf();
        String diskMonCommand = diskHealthStatusConf.getDiskMonCommand();
        if (diskMonCommand != null) {
            try {
                diskCommandResult = utils.getCommandResult(diskMonCommand);
            } catch (OsCommandExecException e) {
                logger.error(e.toString());
            }
            int diskHealth = diskCommandResult.size() > 0 ? Health.GOOD.getHealthCode() : Health.BAD.getHealthCode();
            HashMap<String,Integer> logPriorityMap= (HashMap<String, Integer>) config.getLogPriorityMap();
            int diskHealthPriority=logPriorityMap.getOrDefault(diskMonCommand, DEFAULT_PRIORITY);
            BaseAggregateHealthInfo healthInfo = new DiskAggregateHealthInfo(diskMonCommand, diskHealth,diskHealthPriority);
            aggregateHealthInfo.addAggregateHealthInfo(healthInfo);
        }
    }

}
