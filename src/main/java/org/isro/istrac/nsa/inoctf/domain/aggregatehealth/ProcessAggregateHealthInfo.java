package org.isro.istrac.nsa.inoctf.domain.aggregatehealth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString

public class ProcessAggregateHealthInfo extends  BaseAggregateHealthInfo{


    public ProcessAggregateHealthInfo(String name, int healthCode) {
        super(name, healthCode);
    }
}
