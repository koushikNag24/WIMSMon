package org.isro.istrac.nsa.inoctf.domain;


public enum Health {
    BAD(0),
    GOOD(1),
    UNKNOWN(3);
    private final int healthCode;

    Health(int healthCode) {
        this.healthCode = healthCode;
    }

    public int getHealthCode() {
        return this.healthCode;
    }

}
