package org.isro.istrac.nsa.inoctf.strategy;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.config.Config;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.AggregateHealthInfo;

public class DbmsLogStrategy implements LogStrategy{
    final static Logger logger = Logger.getLogger(DbmsLogStrategy.class);
    @Override
    public void log(@NonNull AggregateHealthInfo healthInfo, @NonNull Config config) {
            logger.info(" saving in DBMS file");
    }
}
