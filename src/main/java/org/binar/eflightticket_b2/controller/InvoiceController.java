package org.binar.eflightticket_b2.controller;

import net.sf.jasperreports.engine.JRException;
import org.binar.eflightticket_b2.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final static Logger log = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;


    @GetMapping("/generatePdf{id}")
    public ResponseEntity<byte[]> generateInvoice(@PathVariable("id") Long bookingId) throws JRException, FileNotFoundException {
        byte[] data = invoiceService.generateInvoice(bookingId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename= invoice.pdf");
        log.info(" Invoice generated ");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
    }
}