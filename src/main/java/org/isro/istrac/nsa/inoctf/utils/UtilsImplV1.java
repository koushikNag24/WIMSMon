package org.isro.istrac.nsa.inoctf.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UtilsImplV1 implements Utils {

    public static final String COMMAND_LINE = "bash";
    public static final String OPTION = "-c";

    @Override
    public void snooze(long snoozeSec)  {
        try {
            TimeUnit.SECONDS.sleep(snoozeSec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public List<String> getCommandResult(String command) {
        List<String> commandResult=new ArrayList<>();
        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command(COMMAND_LINE, OPTION, command);
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                commandResult.add(line);
            }
            boolean exitVal = process.waitFor(1,TimeUnit.SECONDS);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return  commandResult;
    }
}
