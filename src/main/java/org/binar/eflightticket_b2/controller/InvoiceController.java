package org.binar.eflightticket_b2.controller;

import com.google.zxing.WriterException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.BookingService;
import org.binar.eflightticket_b2.service.InvoiceService;
import org.binar.eflightticket_b2.service.impl.InvoiceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<org.binar.eflightticket_b2.payload.ApiResponse> getEticket(HttpServletResponse response, @RequestParam(name = "bookingId") Long bookingId) throws IOException, JRException {
        JasperPrint jasperPrint = invoiceService.generateInvoice(bookingId);
//        response.setContentType("application/x-download");
        response.addHeader("Content-disposition", "attachment; filename=invoice_report.pdf");
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,out);
        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "success", jasperPrint);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/generateticket")
    public void generateEticketPrint(HttpServletResponse response, @RequestParam(name = "bookingId") Long bookingId) throws IOException, JRException {
        JasperPrint jasperPrint = invoiceService.generateInvoice(bookingId);
        response.setContentType("application/x-download");
        response.addHeader("Content-disposition", "attachment; filename=eticket"+bookingId+".pdf");
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,out);
    }

    @GetMapping("/showQRCode")
    public ResponseEntity<ApiResponse> generateQRCode(@RequestParam(name = "bookingId") Long bookingId) throws WriterException, IOException {

        byte[] image;
        image = invoiceService.getQRCodeImage(bookingId, 250, 250);
        String qrCode = Base64.getEncoder().encodeToString(image);

        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "success", qrCode);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoiceQRCode.PNG").body(apiResponse);
    }

}