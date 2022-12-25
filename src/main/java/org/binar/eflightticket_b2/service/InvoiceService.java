package org.binar.eflightticket_b2.service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.FileNotFoundException;

public interface InvoiceService {
    JasperPrint generateInvoice(Long bookingId) throws FileNotFoundException, JRException;
}
