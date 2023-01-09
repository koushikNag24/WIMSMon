package org.isro.istrac.nsa.inoctf.strategy;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.Config;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.AggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.BaseAggregateHealthInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class FileLogStrategy implements LogStrategy{
    public static final char PAD_CHAR = '0';
    public static final String STATUS_LOG_FILE_PREFIX = "Software";
    final static Logger logger = Logger.getLogger(FileLogStrategy.class);
    public static final String FNAME_SEPERATOR = "_";
    public static final String NEXT_LINE = "\n";
    public static final String COMA = ",";
    public static final String EPOCH_KEY = "epoch";
    public static final String STATUS_LOG_FILE_POSTFIX = "Monit.txt";
    public static final int DOY_DIGITS = 3;
    public static final int YEAR_DIGITS = 4;


    @Override
    public void log(@NonNull AggregateHealthInfo healthInfo, @NonNull Config config) {
        LocalDateTime now=LocalDateTime.now();
        String currentDoy= String.valueOf(now.getDayOfYear());
        String currentYear= String.valueOf(now.getYear());
        Path logFile=Paths.get(config.getHealthStatusLogArea()+File.separator+
                STATUS_LOG_FILE_PREFIX+ FNAME_SEPERATOR + padStringWithZero(currentDoy, DOY_DIGITS)+ FNAME_SEPERATOR +padStringWithZero(currentYear, YEAR_DIGITS)+ FNAME_SEPERATOR + STATUS_LOG_FILE_POSTFIX);


        if(!Files.exists(logFile)){
            try {
                Files.createFile(logFile);
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }

        String healthStringToCommit=getHealthString(healthInfo,config);
        try {
            Files.write(logFile,healthStringToCommit.getBytes(),StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error(e.toString());
        }
        cleanOldHealthFileIfDayChanged(config);
    }
    private static  void cleanOldHealthFileIfDayChanged(Config config){
        LocalDateTime now=LocalDateTime.now();
        String yesterdayDoy= String.valueOf(now.getDayOfYear()-1);
        String currentYear= String.valueOf(now.getYear());
        Path logFile=Paths.get(config.getHealthStatusLogArea()+File.separator+
                STATUS_LOG_FILE_PREFIX+ FNAME_SEPERATOR + padStringWithZero(yesterdayDoy, DOY_DIGITS)+ FNAME_SEPERATOR +padStringWithZero(currentYear, YEAR_DIGITS)+ FNAME_SEPERATOR + STATUS_LOG_FILE_POSTFIX);
    }
    private static String getHealthString(@NonNull AggregateHealthInfo healthInfo,Config config){
        StringBuilder healthString =new StringBuilder();
        boolean showKeyWithStatus=Boolean.parseBoolean(config.getShowKeyWithStatus());
        List<BaseAggregateHealthInfo> aggregateHealthInfos=healthInfo.getAggregateHealthInfos();
        Collections.sort(aggregateHealthInfos);
        populateHealth(aggregateHealthInfos, healthString,showKeyWithStatus);

        boolean showEpoch=Boolean.parseBoolean(config.getShowEpoch());
        if(showEpoch){
            boolean showEpochKeyWithStatus=Boolean.parseBoolean(config.getShowEpochKeyWithStatus());
            if(showEpochKeyWithStatus){
                healthString.append(EPOCH_KEY).append(File.pathSeparatorChar).append(healthInfo.getEpoch());
            }else{
                healthString.append(healthInfo.getEpoch());
            }
        }else{
            if(healthString.length()>0){
                healthString.deleteCharAt(healthString.length() - 1);
            }
        }

        healthString.append(NEXT_LINE);
        return healthString.toString();
    }

    private static void populateHealth(List<BaseAggregateHealthInfo> aggregateHealthInfos, StringBuilder healthString, boolean showKeyWithStatus) {

            for (BaseAggregateHealthInfo aggregateHealthInfo : aggregateHealthInfos) {
                if(showKeyWithStatus) {
                    healthString.append(aggregateHealthInfo.getName()).append(File.pathSeparatorChar).append(aggregateHealthInfo.getHealthCode()).append(COMA);
                }else{
                    healthString.append(aggregateHealthInfo.getHealthCode()).append(COMA);
                }
            }


    }

    private static String padStringWithZero(String inputString,int length){
    return String.format("%1$" + length + "s", inputString).replace(' ', PAD_CHAR);


}
}
