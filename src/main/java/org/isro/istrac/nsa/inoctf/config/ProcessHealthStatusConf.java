package org.isro.istrac.nsa.inoctf.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor

public class ProcessHealthStatusConf  {
    private String processMonCommand;
    private List<Process> processList;


}
