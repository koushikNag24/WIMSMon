package org.isro.istrac.nsa.inoctf.strategy;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.Config;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

import static org.isro.istrac.nsa.inoctf.config.ConsoleColors.GREEN_BOLD_BRIGHT;
import static org.isro.istrac.nsa.inoctf.config.ConsoleColors.RESET;

public class FileLogStrategy implements LogStrategy{
    final static Logger logger = Logger.getLogger(FileLogStrategy.class);
    public static final String UNDER_SCORE = "_";
    public static final String NEXT_LINE = "\n";
    public static final String COMA = ",";

    @Override
    public void log(@NonNull AggregateHealthInfo healthInfo, @NonNull Config config) {
        LocalDateTime now=LocalDateTime.now();
        String currentDoy= String.valueOf(now.getDayOfYear());
        String currentYear= String.valueOf(now.getYear());
        Path logFile=Paths.get(config.getHealthStatusLog()+File.separator+"Softy"+UNDER_SCORE+ padStringWithZero(currentDoy,3)+ UNDER_SCORE +padStringWithZero(currentYear,4)+UNDER_SCORE+"Monit.txt");
        if(!Files.exists(logFile)){
            try {
                Files.createFile(logFile);
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }

        String healthStringToCommit=getHealthString(healthInfo);
        try {
            Files.write(logFile,healthStringToCommit.getBytes(),StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }
    private static String getHealthString(@NonNull AggregateHealthInfo healthInfo){
        StringBuilder healthString =new StringBuilder();
        populateDiskHealth(healthInfo, healthString);
        populateFileHealth(healthInfo, healthString);
        populateProcessHealth(healthInfo, healthString);
        populateSystemDHealth(healthInfo, healthString);
        healthString.append("epoch").append(File.pathSeparatorChar).append(healthInfo.getEpoch());
        healthString.append(NEXT_LINE);
        return healthString.toString();
    }

    private static void populateSystemDHealth(AggregateHealthInfo healthInfo, StringBuilder healthString) {
        for(SystemDAggregateHealthInfo systemDAggregateHealthInfo: healthInfo.getSystemDAggregateHealthInfos()){
            healthString.append(systemDAggregateHealthInfo.getName()).append(File.pathSeparatorChar).append(systemDAggregateHealthInfo.getHealthCode()).append(COMA);
        }
    }

    private static void populateProcessHealth(AggregateHealthInfo healthInfo, StringBuilder healthString) {
        for(ProcessAggregateHealthInfo processAggregateHealthInfo: healthInfo.getProcessAggregateHealthInfos()){
            healthString.append(processAggregateHealthInfo.getName()).append(File.pathSeparatorChar).append(processAggregateHealthInfo.getHealthCode()).append(COMA);
        }
    }

    private static void populateFileHealth(AggregateHealthInfo healthInfo, StringBuilder healthString) {
        for(FileAggregateHealthInfo fileAggregateHealthInfo: healthInfo.getFileAggregateHealthInfos()){
            healthString.append(fileAggregateHealthInfo.getName()).append(File.pathSeparatorChar).append(fileAggregateHealthInfo.getHealthCode()).append(COMA);
        }
    }

    private static void populateDiskHealth(AggregateHealthInfo healthInfo, StringBuilder healthString) {
        for(DiskAggregateHealthInfo diskAggregateHealthInfo: healthInfo.getDiskAggregateHealthInfos()){
            healthString.append(diskAggregateHealthInfo.getName()).append(File.pathSeparatorChar).append(diskAggregateHealthInfo.getHealthCode()).append(COMA);
        }
    }

    private String padStringWithZero(String inputString,int length){
    return String.format("%1$" + length + "s", inputString).replace(' ', '0');
}
}
