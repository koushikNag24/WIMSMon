package org.isro.istrac.nsa.inoctf.config;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Process {
    String name;
    int permissibleNoOfAliveInstance;
}
