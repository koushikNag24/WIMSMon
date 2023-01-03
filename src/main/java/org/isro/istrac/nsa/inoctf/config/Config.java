package org.isro.istrac.nsa.inoctf.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.*;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.exception.ConfigException;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Config {
    public final File CONFIG_FILE =new File("/opt/SoftyMon/config.yaml");
    final static Logger logger = Logger.getLogger(Config.class);
    private long sleepSeconds;

    private String isContinue;

    private String epochFormatter;


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
            appConfig =optionalAppConfig.orElseThrow(()->new ConfigException("Software Configuration Read Error"));
            logger.info(ConsoleColors.CYAN_BOLD_BRIGHT+appConfig+ ConsoleColors.RESET);
        } catch (ConfigException e) {
            logger.info(e.getMessage());
        }
        return appConfig;
    }
    public boolean isConfigFileUpdated(long prevCfgFileUpdateEpoch, long currentCfgFileUpdateEpoch) {
        return prevCfgFileUpdateEpoch != currentCfgFileUpdateEpoch;
    }

}
