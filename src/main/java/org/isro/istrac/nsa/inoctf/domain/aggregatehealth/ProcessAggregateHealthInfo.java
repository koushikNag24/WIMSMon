package org.isro.istrac.nsa.inoctf.domain.aggregatehealth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ProcessAggregateHealthInfo extends  BaseAggregateHealthInfo{
    int a;
    public ProcessAggregateHealthInfo(String name, int healthCode,int a) {

        super(name, healthCode);
        this.a=a;
    }
}
