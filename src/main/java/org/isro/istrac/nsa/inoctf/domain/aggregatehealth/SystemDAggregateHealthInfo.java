package org.isro.istrac.nsa.inoctf.domain.aggregatehealth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString

public class SystemDAggregateHealthInfo extends  BaseAggregateHealthInfo{


    public SystemDAggregateHealthInfo(String name, int healthCode) {
        super(name, healthCode);
    }
}
