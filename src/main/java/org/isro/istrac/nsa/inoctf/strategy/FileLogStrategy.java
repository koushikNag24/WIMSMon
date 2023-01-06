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
    }
    private static String getHealthString(@NonNull AggregateHealthInfo healthInfo,Config config){
        StringBuilder healthString =new StringBuilder();
        boolean showKeyWithStatus=Boolean.parseBoolean(config.getShowKeyWithStatus());
        populateDiskHealth(healthInfo, healthString,showKeyWithStatus);
        populateFileHealth(healthInfo, healthString,showKeyWithStatus);
        populateProcessHealth(healthInfo, healthString,showKeyWithStatus);
        populateSystemDHealth(healthInfo, healthString,showKeyWithStatus);

        boolean showEpoch=Boolean.parseBoolean(config.getShowEpoch());
        if(showEpoch){
            boolean showEpochKeyWithStatus=Boolean.parseBoolean(config.getShowEpochKeyWithStatus());
            if(showEpochKeyWithStatus){
                healthString.append(EPOCH_KEY).append(File.pathSeparatorChar).append(healthInfo.getEpoch());
            }else{
                healthString.append(healthInfo.getEpoch());
            }
        }else{
            healthString.deleteCharAt(healthString.length() - 1);
        }

        healthString.append(NEXT_LINE);
        return healthString.toString();
    }

    private static void populateSystemDHealth(AggregateHealthInfo healthInfo, StringBuilder healthString,boolean showKeyWithStatus) {

        if(healthInfo.getSystemDAggregateHealthInfos()!=null) {
            for (BaseAggregateHealthInfo systemDAggregateHealthInfo : healthInfo.getSystemDAggregateHealthInfos()) {
                if(showKeyWithStatus){
                    healthString.append(systemDAggregateHealthInfo.getName()).append(File.pathSeparatorChar).append(systemDAggregateHealthInfo.getHealthCode()).append(COMA);
                }else {
                    healthString.append(systemDAggregateHealthInfo.getHealthCode()).append(COMA);
                }
            }
        }

    }

    private static void populateProcessHealth(AggregateHealthInfo healthInfo, StringBuilder healthString,boolean showKeyWithStatus) {
        if(healthInfo.getProcessAggregateHealthInfos()!=null) {
            for (BaseAggregateHealthInfo processAggregateHealthInfo : healthInfo.getProcessAggregateHealthInfos()) {
                if(showKeyWithStatus) {
                    healthString.append(processAggregateHealthInfo.getName()).append(File.pathSeparatorChar).append(processAggregateHealthInfo.getHealthCode()).append(COMA);
                }else{
                    healthString.append(processAggregateHealthInfo.getHealthCode()).append(COMA);
                }
            }
        }

    }

    private static void populateFileHealth(AggregateHealthInfo healthInfo, StringBuilder healthString,boolean showKeyWithStatus) {
        if(healthInfo.getFileAggregateHealthInfos()!=null) {
            for (BaseAggregateHealthInfo fileAggregateHealthInfo : healthInfo.getFileAggregateHealthInfos()) {
                if(showKeyWithStatus) {
                    healthString.append(fileAggregateHealthInfo.getName()).append(File.pathSeparatorChar).append(fileAggregateHealthInfo.getHealthCode()).append(COMA);
                }else{
                    healthString.append(fileAggregateHealthInfo.getHealthCode()).append(COMA);
                }
            }
        }

    }

    private static void populateDiskHealth(AggregateHealthInfo healthInfo, StringBuilder healthString,boolean showKeyWithStatus) {
        if (healthInfo.getDiskAggregateHealthInfos() != null) {
            for (BaseAggregateHealthInfo diskAggregateHealthInfo : healthInfo.getDiskAggregateHealthInfos()) {
                if(showKeyWithStatus) {
                    healthString.append(diskAggregateHealthInfo.getName()).append(File.pathSeparatorChar).append(diskAggregateHealthInfo.getHealthCode()).append(COMA);
                }else{
                    healthString.append(diskAggregateHealthInfo.getHealthCode()).append(COMA);
                }
            }
        }

    }

    private String padStringWithZero(String inputString,int length){
    return String.format("%1$" + length + "s", inputString).replace(' ', PAD_CHAR);


}
}
