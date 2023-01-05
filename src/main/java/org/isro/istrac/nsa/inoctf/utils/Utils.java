package org.isro.istrac.nsa.inoctf.utils;

import org.isro.istrac.nsa.inoctf.exception.OsCommandExecException;

import java.util.List;

public interface Utils {
    void snooze(long snoozeSec) ;



    List<String> getCommandResult(String command) throws OsCommandExecException;
}
