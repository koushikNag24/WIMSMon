package org.isro.istrac.nsa.inoctf.model.aggregatehealth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public  class BaseAggregateHealthInfo implements Comparable<BaseAggregateHealthInfo>{
    protected String name;
    protected int healthCode;
    protected int priority;
    @Override
    public int compareTo(BaseAggregateHealthInfo healthInfo){
        if(healthInfo.getPriority() == this.getPriority()){
            return this.getName().compareTo(healthInfo.getName());
        }
        return Integer.compare(healthInfo.getPriority(),this.getPriority());
    }


}
