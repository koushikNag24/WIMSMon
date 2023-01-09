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
public class SystemDHealthStatusConf  {
    private String systemMonCommand;
    private List<String> serviceList;


}
