package org.isro.istrac.nsa.inoctf;

import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.domain.*;
import org.isro.istrac.nsa.inoctf.utils.Utils;
import org.isro.istrac.nsa.inoctf.utils.UtilsImplV1;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    final static Logger logger = Logger.getLogger(App.class);
    public static void main( String[] args )
    {
        Utils utilsV1=new UtilsImplV1();
        HealthStatus diskHealthStatus=new DiskHealthStatus(utilsV1);
        HealthStatus fileHealthStatus=new FileHealthStatus(utilsV1);
        HealthStatus processHealthStatus=new ProcessHealthStatus(utilsV1);
        HealthStatus systemDHealthStatus=new SystemDHealthStatus(utilsV1);

        List<HealthStatus> healthStatuses=new ArrayList<>();
        healthStatuses.add(diskHealthStatus);
        healthStatuses.add(fileHealthStatus);
        healthStatuses.add(processHealthStatus);
        healthStatuses.add(systemDHealthStatus);
        HealthFacade healthFacade=new HealthFacade(utilsV1,healthStatuses);
        healthFacade.processHealthStatus();
        logger.info("Software Terminated ");
    }
}
