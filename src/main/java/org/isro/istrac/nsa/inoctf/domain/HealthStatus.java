package org.isro.istrac.nsa.inoctf.domain;

import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.AggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.exception.InternalAggregateMonException;

import java.io.IOException;

public interface HealthStatus {

    AggregateHealthInfo aggregateHealthInfo = new AggregateHealthInfo();

    void logHealthStatus() throws InternalAggregateMonException, IOException;
}
