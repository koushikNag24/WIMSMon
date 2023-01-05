package org.isro.istrac.nsa.inoctf.domain;

import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.AggregateHealthInfo;
import org.isro.istrac.nsa.inoctf.exception.InternalAggregateMonException;

public interface HealthStatus {

    AggregateHealthInfo aggregateHealthInfo = new AggregateHealthInfo();

    void logHealthStatus() throws InternalAggregateMonException;
}
