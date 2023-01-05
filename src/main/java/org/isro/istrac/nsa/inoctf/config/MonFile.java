package org.isro.istrac.nsa.inoctf.config;

import lombok.*;

import java.io.File;
import java.nio.file.Path;
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonFile {
    String file;
    long permissibleUpdateWindowInSec;
}
