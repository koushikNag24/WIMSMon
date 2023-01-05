package org.isro.istrac.nsa.inoctf.domain.aggregatehealth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@AllArgsConstructor
public class DiskAggregateHealthInfo extends  BaseAggregateHealthInfo{
    String name;
    int healthCode;

}
