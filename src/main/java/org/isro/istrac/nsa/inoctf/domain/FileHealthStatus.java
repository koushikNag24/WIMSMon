package org.isro.istrac.nsa.inoctf.domain;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.FileHealthStatusConf;
import org.isro.istrac.nsa.inoctf.config.MonFile;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.FileAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.ProcessAggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.exception.InternalAggregateMonException;
import org.isro.istrac.nsa.inoctf.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.isro.istrac.nsa.inoctf.config.ConsoleColors.RED_BOLD_BRIGHT;
import static org.isro.istrac.nsa.inoctf.config.ConsoleColors.RESET;

public class FileHealthStatus implements HealthStatus{

    final static Logger logger = Logger.getLogger(FileHealthStatus.class);
    private final FileHealthStatusConf config;
     Utils utils;
    public FileHealthStatus(@NonNull Utils utils,@NonNull FileHealthStatusConf config) {
        this.config=config;
        this.utils = utils;
    }
    public void logHealthStatus() throws InternalAggregateMonException, IOException {
        logger.info(" Processing File Health Status");
        List<FileAggregateHealthInfo> fileAggregateHealthInfos = new ArrayList<>();
        List<MonFile> filesToMonitor = config.getMonFiles();
        for(MonFile aMonFile : filesToMonitor ){
            Path file= Paths.get(aMonFile.getFile());
            long permissibleUpdateWindowInSec=aMonFile.getPermissibleUpdateWindowInSec();
            BasicFileAttributes fileAttributes= Files.readAttributes(file, BasicFileAttributes.class);
            Instant fileLastModifiedInstant=fileAttributes.lastModifiedTime().toInstant();
            Instant currentInstant=Instant.now();
            Instant fiveSecondsBefore=currentInstant.minusSeconds(permissibleUpdateWindowInSec);
            int fileHealthCode=fileLastModifiedInstant.isBefore(fiveSecondsBefore)?Health.BAD.getHealthCode() : Health.GOOD.getHealthCode();
            FileAggregateHealthInfo fileAggregateHealthInfo=new FileAggregateHealthInfo(file.getFileName().toString(),fileHealthCode);
            fileAggregateHealthInfos.add(fileAggregateHealthInfo);
        }
        aggregateHealthInfo.setFileAggregateHealthInfos(fileAggregateHealthInfos);
    }
}
