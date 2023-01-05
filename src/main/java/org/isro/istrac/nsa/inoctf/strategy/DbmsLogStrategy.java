package org.isro.istrac.nsa.inoctf.strategy;

import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.domain.aggregatehealth.AggregateHealthInfo;

public class DbmsLogStrategy implements LogStrategy{
    final static Logger logger = Logger.getLogger(DbmsLogStrategy.class);
    @Override
    public void log(AggregateHealthInfo healthInfo) {
            logger.info(" saving in log file");
    }
}
