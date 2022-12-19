package org.binar.eflightticket_b2.dto;

public enum BAGGAGE {

    KG0(0),
    KG5(324_000),
    KG10(648_000),
    KG15(972_000),
    KG20(1_296_000);

    public final Integer price;

    BAGGAGE(Integer price) {
        this.price = price;
    }

}
