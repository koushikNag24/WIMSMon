package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.Config;
import org.isro.istrac.nsa.inoctf.config.FileHealthStatusConf;
import org.isro.istrac.nsa.inoctf.config.MonFile;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.AggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.BaseAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.FileAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import static org.isro.istrac.nsa.inoctf.domain.DiskHealthStatus.DEFAULT_PRIORITY;

public class FileHealthStatus implements HealthStatus{

    final static Logger logger = Logger.getLogger(FileHealthStatus.class);
    private final Config config;
     Utils utils;

    AggregateHealthInfo aggregateHealthInfo;
    public FileHealthStatus(@NonNull Utils utils,@NonNull Config config,@NonNull AggregateHealthInfo aggregateHealthInfo) {
        this.config=config;
        this.aggregateHealthInfo=aggregateHealthInfo;
        this.utils = utils;
    }
    public void logHealthStatus() {
        FileHealthStatusConf fileHealthStatusConf=config.getFileHealthStatusConf();
        List<MonFile> filesToMonitor = fileHealthStatusConf.getMonFiles();
        if (filesToMonitor != null) {
            for (MonFile aMonFile : filesToMonitor) {
                Path file = Paths.get(aMonFile.getFile());
                long permissibleUpdateWindowInSec = aMonFile.getPermissibleUpdateWindowInSec();
                int fileHealthCode;
                BasicFileAttributes fileAttributes;
                try {
                    fileAttributes = Files.readAttributes(file, BasicFileAttributes.class);
                    Instant fileLastModifiedInstant = fileAttributes.lastModifiedTime().toInstant();
                    Instant currentInstant = Instant.now();
                    Instant permissibleUpdateWindowSecondsBefore = currentInstant.minusSeconds(permissibleUpdateWindowInSec);
                    fileHealthCode = fileLastModifiedInstant.isBefore(permissibleUpdateWindowSecondsBefore) ? Health.BAD.getHealthCode() : Health.GOOD.getHealthCode();
                } catch (IOException e) {
                    logger.error(e.toString());
                    // change such that if file is not present it should outout 0 //
                    fileHealthCode = Health.UNKNOWN.getHealthCode();

                }
                HashMap<String,Integer> logPriorityMap= (HashMap<String, Integer>) config.getLogPriorityMap();
                int fileHealthPriority=logPriorityMap.getOrDefault(String.valueOf(file.getFileName()),DEFAULT_PRIORITY);
                BaseAggregateHealthInfo fileAggregateHealthInfo = new FileAggregateHealthInfo(file.getFileName().toString(), fileHealthCode,fileHealthPriority);
                aggregateHealthInfo.addAggregateHealthInfo(fileAggregateHealthInfo);
            }

        }
    }
}
