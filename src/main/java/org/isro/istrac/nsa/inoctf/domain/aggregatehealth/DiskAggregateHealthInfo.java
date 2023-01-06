package org.isro.istrac.nsa.inoctf.domain.aggregatehealth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString

public class DiskAggregateHealthInfo extends  BaseAggregateHealthInfo{


    public DiskAggregateHealthInfo(String name, int healthCode) {
        super(name, healthCode);
    }
}
