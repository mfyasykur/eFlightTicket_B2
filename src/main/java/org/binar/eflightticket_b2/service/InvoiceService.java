package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.entity.Invoice;

import java.util.Arrays;
import java.util.List;

public class InvoiceService {
    public static List<Invoice> generateInvoiceList(){
        return Arrays.asList(
                new Invoice(1L, 1L, 1L,
                        "cityA", "countryA",
                        "airportA", "airportB",
                        "a", "b", (short) 1),
                new Invoice(2L, 2L, 2L,
                        "cityB", "countryB",
                        "airportC", "airportD",
                        "c", "d", (short) 2)

        );
    }
}
