package org.binar.eflightticket_b2.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.binar.eflightticket_b2.service.InvoiceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

@RestController
public class InvoiceController {
    @GetMapping(value = "/generatePdf")
    public String generatePdf() throws FileNotFoundException, JRException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
                InvoiceService.generateInvoiceList());
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/ETicket.jrxml"));
        HashMap<String, Object> map = new HashMap<>();
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        JasperExportManager.exportReportToPdfFile(report, "ETicket.pdf");
        return "generated";
    }

    @GetMapping(value = "/getPdf")
    public ResponseEntity<byte[]> getPdf() throws FileNotFoundException, JRException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
                InvoiceService.generateInvoiceList());
        JasperReport compileReport = JasperCompileManager
                .compileReport(new FileInputStream("src/main/resources/ETicket.jrxml"));

        HashMap<String, Object> map = new HashMap<>();
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=ETicket.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
    }
}