package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.ProcessHealthStatusConf;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.AggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.BaseAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.ProcessAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.exception.OsCommandExecException;
import org.isro.istrac.nsa.inoctf.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProcessHealthStatus implements HealthStatus {
    public static final String PROCESS_NAME_PLACEHOLDER = "process_name";
    AggregateHealthInfo aggregateHealthInfo;
    private final ProcessHealthStatusConf config;
    Utils utils;

    public ProcessHealthStatus(@NonNull Utils utils, @NonNull ProcessHealthStatusConf config,@NonNull AggregateHealthInfo aggregateHealthInfo) {
        this.utils = utils;
        this.aggregateHealthInfo=aggregateHealthInfo;
        this.config = config;
    }
    final static Logger logger = Logger.getLogger(ProcessHealthStatus.class);
    public void logHealthStatus() {
        List<BaseAggregateHealthInfo> processAggregateHealthInfos = new ArrayList<>();
        List<String> processResult = new ArrayList<>();
        List<String> processesToMonitor = config.getProcessList();
        String processMonCommand = config.getProcessMonCommand();
        boolean performProcessHealthCheck=processesToMonitor!=null && processMonCommand!=null;
        if(performProcessHealthCheck) {
            for (String aProcessToMonitor : processesToMonitor) {
                String monCommand = processMonCommand.replace(PROCESS_NAME_PLACEHOLDER, aProcessToMonitor);
                try {
                    processResult = utils.getCommandResult(monCommand);
                } catch (OsCommandExecException e) {
                    logger.error(e.getMessage());
                }
                int processHealth = processResult.size() > 0 ? Health.GOOD.getHealthCode() : Health.BAD.getHealthCode();
                BaseAggregateHealthInfo healthInfo = new ProcessAggregateHealthInfo(aProcessToMonitor, processHealth);
                processAggregateHealthInfos.add(healthInfo);
                aggregateHealthInfo.setProcessAggregateHealthInfos(processAggregateHealthInfos);

            }
        }
    }
}
