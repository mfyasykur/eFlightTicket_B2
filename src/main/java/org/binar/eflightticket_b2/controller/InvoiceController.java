package org.binar.eflightticket_b2.controller;

import com.google.zxing.WriterException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.binar.eflightticket_b2.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final static Logger log = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;


    @GetMapping("/showticket")
    public void getEticket(HttpServletResponse response, @RequestParam(name = "bookingId") Long bookingId) throws IOException, JRException {
        JasperPrint jasperPrint = invoiceService.generateInvoice(bookingId);
        response.addHeader("Content-disposition", "inline; filename=invoice_report.pdf");
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdf(jasperPrint);
    }

    @GetMapping("/generateticket")
    public void generateEticketPrint(HttpServletResponse response, @RequestParam(name = "bookingId") Long bookingId) throws IOException, JRException {
        JasperPrint jasperPrint = invoiceService.generateInvoice(bookingId);
        response.addHeader("Content-disposition", "attachment; filename=eticket"+bookingId+".pdf");
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
    }

    @GetMapping("/showQRCode")
    public String getQRCode(@RequestParam(name = "bookingId") Long bookingId) throws WriterException, IOException {

        byte[] image;
        image = invoiceService.generateQRCodeImage(bookingId, 250, 250);
        String qrCode = Base64.getEncoder().encodeToString(image);
        log.info("QR Code has successfully generated");

        return qrCode;
    }

    @GetMapping("/generateQRCode")
    public ResponseEntity<byte[]> generateQRCode(@RequestParam Long bookingId) throws WriterException, IOException {

        byte[] image;
        image = invoiceService.generateQRCodeImage(bookingId, 250, 250);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Invoice-QRCode-" + bookingId + ".png").body(image);
    }

}