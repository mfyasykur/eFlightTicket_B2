package org.binar.eflightticket_b2.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface InvoiceService {
    byte[] generateInvoice(Long bookingId) throws FileNotFoundException, JRException;
}
