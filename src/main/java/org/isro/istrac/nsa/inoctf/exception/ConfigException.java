package org.isro.istrac.nsa.inoctf.exception;

import java.util.function.Supplier;

public class ConfigException extends Exception {
    public ConfigException(String errorMessage){
        super(errorMessage);
    }


}
