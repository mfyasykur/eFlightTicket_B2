package org.binar.eflightticket_b2.service;

import com.google.zxing.WriterException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.IOException;

public interface InvoiceService {
    JasperPrint generateInvoice(Long bookingId) throws IOException, JRException;

    public byte[] generateQRCodeImage(Long bookingId, int width, int height) throws WriterException, IOException;

}
