package org.isro.istrac.nsa.inoctf.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
@Setter
@Getter
@ToString
public class Config {

    private static final String configFile="/home/lgmdev/IdeaProjects/WIMSMon/src/main/resources/config.json";
    private  int delayTimeInSeconds;
    private int lastUpdatedPermissibleIntervalInSeconds;
    private List<String> systemCtlProcesses;
    private List<String> filesToMonitor;
    private HashMap<String,Integer> processConfig;
    private String statusFile;

    public static Optional<Config> loadConfiguration() throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        Config config = objectMapper.readValue(new File(configFile), Config.class);
        return Optional.ofNullable(config);
    }
}
