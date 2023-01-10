package org.isro.istrac.nsa.inoctf.model;

import org.isro.istrac.nsa.inoctf.exception.InternalAggregateMonException;

import java.io.IOException;

public interface HealthStatus {



    void logHealthStatus() throws InternalAggregateMonException, IOException;
}
