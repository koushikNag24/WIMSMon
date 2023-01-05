package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.DiskHealthStatusConf;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.DiskAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.ProcessAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.exception.InternalAggregateMonException;
import org.isro.istrac.nsa.inoctf.exception.OsCommandExecException;
import org.isro.istrac.nsa.inoctf.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class DiskHealthStatus implements  HealthStatus{
    private DiskHealthStatusConf config;
    private  Utils utils;
    final static Logger logger = Logger.getLogger(DiskHealthStatus.class);

    public DiskHealthStatus(@NonNull Utils utils,@NonNull DiskHealthStatusConf config) {
        this.config=config;
        this.utils = utils;
    }

    public void logHealthStatus() throws InternalAggregateMonException {
        List<DiskAggregateHealthInfo> diskAggregateHealthInfos = new ArrayList<>();
        List<String> diskCommandResult;
        String diskMonCommand = config.getDiskMonCommand();
        try {
            diskCommandResult = utils.getCommandResult(diskMonCommand);
        } catch (OsCommandExecException e) {
            throw new RuntimeException(e);
        }
        int diskHealth = diskCommandResult.size() > 0 ? Health.GOOD.getHealthCode() : Health.BAD.getHealthCode();
        DiskAggregateHealthInfo healthInfo = new DiskAggregateHealthInfo(diskMonCommand, diskHealth);
        diskAggregateHealthInfos.add(healthInfo);
        aggregateHealthInfo.setDiskAggregateHealthInfos(diskAggregateHealthInfos);
    }

}
