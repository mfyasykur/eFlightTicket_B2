package org.binar.eflightticket_b2.service;

import com.google.zxing.WriterException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface InvoiceService {
    JasperPrint generateInvoice(Long bookingId) throws FileNotFoundException, JRException;

    public void generateQRCodeImage(Long bookingId, int width, int height, String filePath) throws WriterException, IOException;

    public byte[] getQRCodeImage(Long bookingId, int width, int height) throws WriterException, IOException;
}
