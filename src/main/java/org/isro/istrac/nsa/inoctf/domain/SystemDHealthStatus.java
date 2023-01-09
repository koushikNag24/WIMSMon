package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.Config;
import org.isro.istrac.nsa.inoctf.config.SystemDHealthStatusConf;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.AggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.BaseAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.SystemDAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.exception.OsCommandExecException;
import org.isro.istrac.nsa.inoctf.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.isro.istrac.nsa.inoctf.domain.DiskHealthStatus.DEFAULT_PRIORITY;

public class SystemDHealthStatus implements  HealthStatus{
    public static final String SERVICE_NAME_PLACE_HOLDER = "service_name";
    public static final String ACTIVE_RUNNING = "active (running)";
    public static final String ACTIVE = "Active:";
    private final Config config;
    private final Utils utils;

    AggregateHealthInfo aggregateHealthInfo;
    public SystemDHealthStatus(@NonNull Utils utils, @NonNull Config config, @NonNull AggregateHealthInfo aggregateHealthInfo) {
        this.config=config;
        this.aggregateHealthInfo=aggregateHealthInfo;
        this.utils = utils;
    }
    final static Logger logger = Logger.getLogger(SystemDHealthStatus.class);

    public void logHealthStatus() {


        List<String> systemDResult = new ArrayList<>();
        SystemDHealthStatusConf systemDHealthStatusConf=config.getSystemDHealthStatusConf();
        List<String> systemServiceToMonitor = systemDHealthStatusConf.getServiceList();
        String systemCmd=systemDHealthStatusConf.getSystemMonCommand();
        if(systemServiceToMonitor!=null){
        for (String systemDToMonitor : systemServiceToMonitor) {
            String monCommand = systemCmd.replace(SERVICE_NAME_PLACE_HOLDER,systemDToMonitor);
            try {
                systemDResult = utils.getCommandResult(monCommand);
            } catch (OsCommandExecException e) {
                logger.error(e.toString());
            }
            int systemDHealth=processSystemDHealth(systemDResult);
            HashMap<String,Integer> logPriorityMap= (HashMap<String, Integer>) config.getLogPriorityMap();
            int systemDHealthPriority=logPriorityMap.getOrDefault(systemDToMonitor,DEFAULT_PRIORITY);
            BaseAggregateHealthInfo healthInfo = new SystemDAggregateHealthInfo(systemDToMonitor, systemDHealth,systemDHealthPriority);
            aggregateHealthInfo.addAggregateHealthInfo(healthInfo);
        }
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
