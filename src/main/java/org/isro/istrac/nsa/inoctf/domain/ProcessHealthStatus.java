package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.Config;
import org.isro.istrac.nsa.inoctf.config.ProcessHealthStatusConf;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.AggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.BaseAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.ProcessAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.exception.OsCommandExecException;
import org.isro.istrac.nsa.inoctf.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.isro.istrac.nsa.inoctf.domain.DiskHealthStatus.DEFAULT_PRIORITY;

public class ProcessHealthStatus implements HealthStatus {
    public static final String PROCESS_NAME_PLACEHOLDER = "process_name";
    AggregateHealthInfo aggregateHealthInfo;
    private final Config config;
    Utils utils;

    public ProcessHealthStatus(@NonNull Utils utils, @NonNull Config config,@NonNull AggregateHealthInfo aggregateHealthInfo) {
        this.utils = utils;
        this.aggregateHealthInfo=aggregateHealthInfo;
        this.config = config;
    }
    final static Logger logger = Logger.getLogger(ProcessHealthStatus.class);
    public void logHealthStatus() {
        List<String> processResult = new ArrayList<>();
        ProcessHealthStatusConf processHealthStatusConf=config.getProcessHealthStatusConf();
        List<String> processesToMonitor = processHealthStatusConf.getProcessList();
        String processMonCommand = processHealthStatusConf.getProcessMonCommand();
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
                HashMap<String,Integer> logPriorityMap= (HashMap<String, Integer>) config.getLogPriorityMap();
                int processHealthPriority=logPriorityMap.getOrDefault(aProcessToMonitor, DEFAULT_PRIORITY);
                BaseAggregateHealthInfo healthInfo = new ProcessAggregateHealthInfo(aProcessToMonitor, processHealth,processHealthPriority);
                aggregateHealthInfo.addAggregateHealthInfo(healthInfo);

            }
        }
    }
}
