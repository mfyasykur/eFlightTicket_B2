package org.binar.eflightticket_b2.controller;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

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
}