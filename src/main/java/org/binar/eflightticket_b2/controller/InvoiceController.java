package org.binar.eflightticket_b2.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.binar.eflightticket_b2.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final static Logger log = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;


    @GetMapping("/generatePdf")
    public ResponseEntity<ApiResponse> getInvoicePrint(HttpServletResponse response, @RequestParam(name = "bookingId") Long bookingId) throws IOException, JRException {
        JasperPrint jasperPrint = invoiceService.generateInvoice(bookingId);
        response.setContentType("application/x-download");
        response.addHeader("Content-disposition", "attachment; filename=invoice_report.pdf");
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,out);
        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "success", jasperPrint);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}