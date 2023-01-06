package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.DiskHealthStatusConf;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.AggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.BaseAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.DiskAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.exception.OsCommandExecException;
import org.isro.istrac.nsa.inoctf.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class DiskHealthStatus implements  HealthStatus{
    private final DiskHealthStatusConf config;
    private final Utils utils;
    final static Logger logger = Logger.getLogger(DiskHealthStatus.class);

    AggregateHealthInfo aggregateHealthInfo;
    public DiskHealthStatus(@NonNull Utils utils,@NonNull DiskHealthStatusConf config,@NonNull AggregateHealthInfo aggregateHealthInfo) {
        this.config=config;
        this.aggregateHealthInfo=aggregateHealthInfo;
        this.utils = utils;
    }

    public void logHealthStatus() {
        List<BaseAggregateHealthInfo> diskAggregateHealthInfos = new ArrayList<>();
        List<String> diskCommandResult = new ArrayList<>();
        String diskMonCommand = config.getDiskMonCommand();
        if (diskMonCommand != null) {
            try {
                diskCommandResult = utils.getCommandResult(diskMonCommand);
            } catch (OsCommandExecException e) {
                logger.error(e.toString());
            }
            int diskHealth = diskCommandResult.size() > 0 ? Health.GOOD.getHealthCode() : Health.BAD.getHealthCode();
            BaseAggregateHealthInfo healthInfo = new DiskAggregateHealthInfo(diskMonCommand, diskHealth);
            diskAggregateHealthInfos.add(healthInfo);
            aggregateHealthInfo.setDiskAggregateHealthInfos(diskAggregateHealthInfos);
        }
    }

}
