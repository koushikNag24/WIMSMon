package org.isro.istrac.nsa.inoctf.config;

import lombok.*;

import java.util.List;
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessHealthStatusConf {
    private String processMonCommand;
    private List<String> processList;
}
