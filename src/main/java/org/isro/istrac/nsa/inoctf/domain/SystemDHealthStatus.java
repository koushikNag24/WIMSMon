package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.SystemDHealthStatusConf;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.ProcessAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.SystemDAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.exception.InternalAggregateMonException;
import org.isro.istrac.nsa.inoctf.exception.OsCommandExecException;
import org.isro.istrac.nsa.inoctf.utils.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.isro.istrac.nsa.inoctf.config.ConsoleColors.RED_BOLD_BRIGHT;
import static org.isro.istrac.nsa.inoctf.config.ConsoleColors.RESET;

public class SystemDHealthStatus implements  HealthStatus{
    public static final String SERVICE_NAME_PLACE_HOLDER = "service_name";
    public static final String ACTIVE_RUNNING = "active (running)";
    public static final String ACTIVE = "Active:";
    private SystemDHealthStatusConf config;
    private Utils utils;
    public SystemDHealthStatus(@NonNull Utils utils,@NonNull SystemDHealthStatusConf config) {
        this.config=config;
        this.utils = utils;
    }
    final static Logger logger = Logger.getLogger(SystemDHealthStatus.class);

    public void logHealthStatus() throws InternalAggregateMonException {
        List<SystemDAggregateHealthInfo> processAggregateHealthInfos = new ArrayList<>();
        List<String> systemDResult;
        List<String> systemServiceToMonitor = config.getServiceList();
        String systemCmd=config.getSystemMonCommand();
        for (String systemDToMonitor : systemServiceToMonitor) {
            String monCommand = systemCmd.replace(SERVICE_NAME_PLACE_HOLDER,systemDToMonitor);
            try {
                systemDResult = utils.getCommandResult(monCommand);
            } catch (OsCommandExecException e) {
                throw new RuntimeException(e);
            }
            int systemDHealth=processSystemDHealth(systemDResult);
            SystemDAggregateHealthInfo healthInfo = new SystemDAggregateHealthInfo(systemDToMonitor, systemDHealth);
            processAggregateHealthInfos.add(healthInfo);
            aggregateHealthInfo.setSystemDAggregateHealthInfos(processAggregateHealthInfos);
        }
    }

    private int processSystemDHealth(List<String> systemDResult) {
        List<String> activeInfo=systemDResult.stream().filter(info->info.contains(ACTIVE)).collect(Collectors.toList());
        if(systemDResult.size()>0){
            String activeStatus= activeInfo.get(0);
            return activeStatus.toLowerCase().contains(ACTIVE_RUNNING) ? Health.GOOD.getHealthCode() : Health.BAD.getHealthCode();
        }else{
            return Health.BAD.getHealthCode();
        }

    }
}
