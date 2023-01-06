package org.isro.istrac.nsa.inoctf.domain.aggregatehealth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public  class BaseAggregateHealthInfo {
    protected String name;
    protected int healthCode;
}
