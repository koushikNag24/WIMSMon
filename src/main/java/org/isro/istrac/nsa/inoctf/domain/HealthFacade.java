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
    List<HealthStatus> healthStatuses;
    HealthStatusHead statusHead;

    Utils utils;

    public HealthFacade(@NonNull Utils utils, List<HealthStatus> healthStatuses) {
       this.healthStatuses=healthStatuses;
        this.utils=utils;
    }
    public void processHealthStatus(){

        boolean isContinue=true;
        Config appConfig=new Config();
        long snoozeSec=0;
        long prevCfgFileUpdateEpoch=0;
        long currentCfgFileUpdateEpoch=appConfig.CONFIG_FILE.lastModified();
        while (isContinue) {
            if(appConfig.isConfigFileUpdated(prevCfgFileUpdateEpoch, currentCfgFileUpdateEpoch)){
                appConfig = appConfig.getConfig(appConfig);
                prevCfgFileUpdateEpoch=currentCfgFileUpdateEpoch;
                logger.info(ConsoleColors.RED_BOLD_BRIGHT+" Config File loaded "+RESET);
            }



            statusHead=new HealthStatusHead(appConfig,new ArrayList<>());
            for(HealthStatus aHealthStatus: healthStatuses){
                statusHead.addHealthStatus(aHealthStatus);
            }


            statusHead.logHealthStatus();

            snoozeSec=appConfig.getSleepSeconds();
            utils.snooze(snoozeSec);
            isContinue=Boolean.valueOf(appConfig.getIsContinue());
            currentCfgFileUpdateEpoch=appConfig.CONFIG_FILE.lastModified();


        }
    }



}
