package org.isro.istrac.nsa.inoctf.config;

import lombok.*;

import java.util.List;
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileHealthStatusConf {
    List<MonFile> monFiles;
}
