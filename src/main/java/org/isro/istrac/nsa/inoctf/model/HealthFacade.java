package org.isro.istrac.nsa.inoctf.model;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.Config;
import org.isro.istrac.nsa.inoctf.model.aggregatehealth.AggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static org.isro.istrac.nsa.inoctf.config.ConsoleColors.GREEN_BOLD_BRIGHT;
import static org.isro.istrac.nsa.inoctf.config.ConsoleColors.RESET;

public class HealthFacade {

    final static Logger logger = Logger.getLogger(HealthFacade.class);
 
    HealthStatusHead statusHead;

    Utils utils;

    public HealthFacade(@NonNull Utils utils) {     
        this.utils=utils;
    }
    public void processHealthStatus(){

        boolean isContinue=true;
        Config appConfig=new Config();
        long snoozeSec;
        long prevCfgFileUpdateEpoch=0;
        long currentCfgFileUpdateEpoch=appConfig.CONFIG_FILE.lastModified();

        while (isContinue) {
            AggregateHealthInfo aggregateHealthInfo = new AggregateHealthInfo();
            List<HealthStatus> healthStatuses=new ArrayList<>();
            if(appConfig.isConfigFilePrevCurrentUpdateTimeNotSame(prevCfgFileUpdateEpoch, currentCfgFileUpdateEpoch)){
                appConfig = Config.getConfig(appConfig);
                prevCfgFileUpdateEpoch=currentCfgFileUpdateEpoch;
                logger.info(GREEN_BOLD_BRIGHT+"[ Configuration File loaded ]"+RESET);
            }
            loadConfigStates(appConfig, healthStatuses,aggregateHealthInfo);
            statusHead=new HealthStatusHead(aggregateHealthInfo,appConfig,new ArrayList<>());
            healthStatuses.forEach(aHealthStatus -> statusHead.addHealthStatus(aHealthStatus));
            statusHead.logHealthStatus();

            snoozeSec=appConfig.getSleepSeconds();
            utils.snooze(snoozeSec);
            isContinue=Boolean.parseBoolean(appConfig.getIsContinue());
            currentCfgFileUpdateEpoch=appConfig.CONFIG_FILE.lastModified();

        }
    }

    private void loadConfigStates(Config appConfig, List<HealthStatus> healthStatuses,AggregateHealthInfo aggregateHealthInfo) {
        loadSystemDConfigState(appConfig, healthStatuses,aggregateHealthInfo);
        loadDiskConfigStates(appConfig, healthStatuses,aggregateHealthInfo);
        loadFileConfigState(appConfig, healthStatuses,aggregateHealthInfo);
        loadProcessConfigState(appConfig, healthStatuses,aggregateHealthInfo);
    }

    private void loadProcessConfigState(Config appConfig, List<HealthStatus> healthStatuses,AggregateHealthInfo aggregateHealthInfo) {
        if(appConfig.getProcessHealthStatusConf()!=null){
            HealthStatus processHealthStatus=new ProcessHealthStatus(utils, appConfig,aggregateHealthInfo);
            healthStatuses.add(processHealthStatus);
        }
    }

    private void loadFileConfigState(Config appConfig, List<HealthStatus> healthStatuses,AggregateHealthInfo aggregateHealthInfo) {
        if(appConfig.getFileHealthStatusConf()!=null){
            HealthStatus fileHealthStatus=new FileHealthStatus(utils, appConfig,aggregateHealthInfo);
            healthStatuses.add(fileHealthStatus);
        }
    }

    private void loadDiskConfigStates(Config appConfig, List<HealthStatus> healthStatuses,AggregateHealthInfo aggregateHealthInfo) {
        if(appConfig.getDiskHealthStatusConf()!=null){
            HealthStatus diskHealthStatus=new DiskHealthStatus(utils, appConfig,aggregateHealthInfo);
            healthStatuses.add(diskHealthStatus);
        }
    }

    private void loadSystemDConfigState(Config appConfig, List<HealthStatus> healthStatuses,AggregateHealthInfo aggregateHealthInfo) {

        if(appConfig.getSystemDHealthStatusConf()!=null){
            HealthStatus systemDHealthStatus=new SystemDHealthStatus(utils, appConfig,aggregateHealthInfo);
            healthStatuses.add(systemDHealthStatus);
        }

    }
}
