package org.isro.istrac.nsa.inoctf.utils;

import lombok.extern.slf4j.Slf4j;
import org.isro.istrac.nsa.inoctf.domain.Health;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class UtilsImplV1 implements Utils {
    @Override
    public void snooze(long snoozeSec)  {
        try {
            TimeUnit.SECONDS.sleep(snoozeSec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int fetchStatus() {
        return new Random().nextInt() %2==0? Health.GOOD.getHealthCode() :Health.BAD.getHealthCode();
    }
}
