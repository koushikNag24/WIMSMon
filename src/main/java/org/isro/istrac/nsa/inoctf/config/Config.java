package org.isro.istrac.nsa.inoctf.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.*;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.exception.ConfigException;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.isro.istrac.nsa.inoctf.config.ConsoleColors.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Config {
    public final File CONFIG_FILE =new File("/opt/SoftwareConfigurations/SoftwareMon/config.yaml");
    final static Logger logger = Logger.getLogger(Config.class);
    private long sleepSeconds;

    private String isContinue;

    private String epochFormatter;

    private ProcessHealthStatusConf processHealthStatusConf;
    private FileHealthStatusConf fileHealthStatusConf;
    private SystemDHealthStatusConf systemDHealthStatusConf;
    private DiskHealthStatusConf diskHealthStatusConf;
    private String healthStatusLogArea;
    private String showKeyWithStatus;
    private  String showEpochKeyWithStatus;
    private String showEpoch;

    protected Map<String,Integer> logPriorityMap;



    private  Optional<Config> loadConfiguration()  {

        ObjectMapper  mapper=new ObjectMapper(new YAMLFactory());
        Config config = null;
        try {
            config = mapper.readValue(CONFIG_FILE, Config.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
//            throw new RuntimeException(e);
        }
          return Optional.ofNullable(config);
    }
    public static Config getConfig(Config appConfig) {
        Optional<Config> optionalAppConfig= appConfig.loadConfiguration();
        try {
            appConfig =optionalAppConfig.orElseThrow(()->new ConfigException("Invalid Software Configuration"));
        } catch (ConfigException e) {
            logger.info(RED_BOLD_BRIGHT+ " [ Tentative Reason : Configuration File is not in YAML format as expected by the software! ] "+e.getMessage()+RESET);
        }
        return appConfig;
    }
    public boolean isConfigFilePrevCurrentUpdateTimeNotSame(long prevCfgFileUpdateEpoch, long currentCfgFileUpdateEpoch) {
        return prevCfgFileUpdateEpoch != currentCfgFileUpdateEpoch;
    }

}
