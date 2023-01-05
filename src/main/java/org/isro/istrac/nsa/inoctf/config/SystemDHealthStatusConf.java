package org.isro.istrac.nsa.inoctf.config;

import lombok.*;

import java.util.List;
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemDHealthStatusConf {
    private String systemMonCommand;
    private List<String> serviceList;
}
