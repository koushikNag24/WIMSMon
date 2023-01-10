package org.isro.istrac.nsa.inoctf.model.aggregatehealth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString

public class ProcessAggregateHealthInfo extends  BaseAggregateHealthInfo{


    public ProcessAggregateHealthInfo(String name, int healthCode,int priority) {
        super(name, healthCode,priority);
    }
}
