package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;
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
import java.util.ArrayList;
import java.util.List;

public class FileHealthStatus implements HealthStatus{

    final static Logger logger = Logger.getLogger(FileHealthStatus.class);
    private final FileHealthStatusConf config;
     Utils utils;

    AggregateHealthInfo aggregateHealthInfo;
    public FileHealthStatus(@NonNull Utils utils,@NonNull FileHealthStatusConf config,@NonNull AggregateHealthInfo aggregateHealthInfo) {
        this.config=config;
        this.aggregateHealthInfo=aggregateHealthInfo;
        this.utils = utils;
    }
    public void logHealthStatus() {
        List<BaseAggregateHealthInfo> fileAggregateHealthInfos = new ArrayList<>();
        List<MonFile> filesToMonitor = config.getMonFiles();
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

                BaseAggregateHealthInfo fileAggregateHealthInfo = new FileAggregateHealthInfo(file.getFileName().toString(), fileHealthCode);
                fileAggregateHealthInfos.add(fileAggregateHealthInfo);
            }
            aggregateHealthInfo.setFileAggregateHealthInfos(fileAggregateHealthInfos);
        }
    }
}
