package org.isro.istrac.nsa.inoctf.domain;

import org.isro.istrac.nsa.inoctf.exception.InternalAggregateMonException;
import org.isro.istrac.nsa.inoctf.utils.Utils;

public interface HealthStatus {

    AggregateHealthInfo aggregateHealthInfo = new AggregateHealthInfo();

    void logHealthStatus() throws InternalAggregateMonException;
}
