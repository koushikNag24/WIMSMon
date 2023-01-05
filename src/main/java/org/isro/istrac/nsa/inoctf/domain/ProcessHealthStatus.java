package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;

import org.isro.istrac.nsa.inoctf.config.ConsoleColors;
import org.isro.istrac.nsa.inoctf.config.ProcessHealthStatusConf;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.ProcessAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.exception.InternalAggregateMonException;
import org.isro.istrac.nsa.inoctf.exception.OsCommandExecException;
import org.isro.istrac.nsa.inoctf.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProcessHealthStatus implements HealthStatus {
    public static final String PROCESS_NAME_PLACEHOLDER = "process_name";
    private ProcessHealthStatusConf config;
    Utils utils;

    public ProcessHealthStatus(@NonNull Utils utils, @NonNull ProcessHealthStatusConf config) {
        this.utils = utils;
        this.config = config;
    }
    final static Logger logger = Logger.getLogger(ProcessHealthStatus.class);
    public void logHealthStatus() throws InternalAggregateMonException {
        List<ProcessAggregateHealthInfo> processAggregateHealthInfos = new ArrayList<>();
        List<String> processResult;
        List<String> processesToMonitor = config.getProcessList();
        String processMonCommand = config.getProcessMonCommand();
        for (String aProcessToMonitor : processesToMonitor) {
            String monCommand = processMonCommand.replace(PROCESS_NAME_PLACEHOLDER, aProcessToMonitor);
            try {
                processResult = utils.getCommandResult(monCommand);
            } catch (OsCommandExecException e) {
                throw new RuntimeException(e);
            }
            int processHealth = processResult.size() > 0 ? Health.GOOD.getHealthCode() : Health.BAD.getHealthCode();
            ProcessAggregateHealthInfo healthInfo = new ProcessAggregateHealthInfo(aProcessToMonitor, processHealth);
            processAggregateHealthInfos.add(healthInfo);
            aggregateHealthInfo.setProcessAggregateHealthInfos(processAggregateHealthInfos);

        }
    }
}
