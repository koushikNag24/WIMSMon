package org.isro.istrac.nsa.inoctf;

import org.apache.log4j.Logger;
import org.isro.istrac.nsa.inoctf.model.*;
import org.isro.istrac.nsa.inoctf.utils.Utils;
import org.isro.istrac.nsa.inoctf.utils.UtilsImplV1;

public class App 
{
    final static Logger logger = Logger.getLogger(App.class);
    public static void main( String[] args )
    {
        Utils utilsV1=new UtilsImplV1();
        HealthFacade healthFacade=new HealthFacade(utilsV1);
        healthFacade.processHealthStatus();
        logger.info("Software Terminated ");
    }
}
