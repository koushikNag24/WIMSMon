package org.isro.istrac.nsa.inoctf.strategy;

import org.isro.istrac.nsa.inoctf.config.Config;
import org.isro.istrac.nsa.inoctf.model.aggregatehealth.AggregateHealthInfo;

public interface LogStrategy {

    public void log(AggregateHealthInfo healthInfo, Config config);
}
