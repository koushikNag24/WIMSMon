package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.Config;
import org.isro.istrac.nsa.inoctf.config.ConsoleColors;
import org.isro.istrac.nsa.inoctf.exception.ConfigException;
import org.isro.istrac.nsa.inoctf.utils.Utils;
import org.isro.istrac.nsa.inoctf.utils.UtilsImplV1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.isro.istrac.nsa.inoctf.config.ConsoleColors.*;

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
        long snoozeSec=0;
        long prevCfgFileUpdateEpoch=0;
        long currentCfgFileUpdateEpoch=appConfig.CONFIG_FILE.lastModified();
        List<HealthStatus> healthStatuses=new ArrayList<>();
        while (isContinue) {
            if(appConfig.isConfigFileUpdated(prevCfgFileUpdateEpoch, currentCfgFileUpdateEpoch)){
                appConfig = Config.getConfig(appConfig);
                prevCfgFileUpdateEpoch=currentCfgFileUpdateEpoch;
                HealthStatus diskHealthStatus=new DiskHealthStatus(utils,appConfig.getDiskHealthStatusConf());
                HealthStatus fileHealthStatus=new FileHealthStatus(utils,appConfig.getFileHealthStatusConf());
                HealthStatus processHealthStatus=new ProcessHealthStatus(utils,appConfig.getProcessHealthStatusConf());
                HealthStatus systemDHealthStatus=new SystemDHealthStatus(utils,appConfig.getSystemDHealthStatusConf());


                healthStatuses.add(diskHealthStatus);
                healthStatuses.add(fileHealthStatus);
                healthStatuses.add(processHealthStatus);
                healthStatuses.add(systemDHealthStatus);
            }
          




            statusHead=new HealthStatusHead(appConfig,new ArrayList<>());
            for(HealthStatus aHealthStatus: healthStatuses){
                
                statusHead.addHealthStatus(aHealthStatus);
            }


            statusHead.logHealthStatus();

            snoozeSec=appConfig.getSleepSeconds();
            utils.snooze(snoozeSec);
            isContinue=Boolean.parseBoolean(appConfig.getIsContinue());
            currentCfgFileUpdateEpoch=appConfig.CONFIG_FILE.lastModified();


        }
    }



}
