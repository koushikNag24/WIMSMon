package org.isro.istrac.nsa.inoctf;

import lombok.extern.slf4j.Slf4j;
import org.isro.istrac.nsa.inoctf.config.Config;
import org.isro.istrac.nsa.inoctf.exception.ConfigurationException;

import java.io.IOException;

/**
 * Hello world!
 *
 */

public class App 
{
    public static void main( String[] args ) {
        System.out.println("Hello World!");
        Config config;
        try {
            config = Config.loadConfiguration().orElseThrow(() -> new ConfigurationException("Configuration Load Error"));
        } catch (IOException | ConfigurationException e) {
            throw new RuntimeException(e);
        }
        System.out.println(config);

    }
}
