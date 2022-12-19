package org.binar.eflightticket_b2.service.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.binar.eflightticket_b2.entity.Invoice;
import org.binar.eflightticket_b2.repository.InvoiceRepository;
import org.binar.eflightticket_b2.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final static Logger log =  LoggerFactory.getLogger(InvoiceServiceImpl.class);
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public byte[] generateInvoice(Long bookingId) throws FileNotFoundException, JRException {

        log.info("Generating pdf invoice for id order {}", bookingId);

        List<Invoice> invoices = invoiceRepository.get(bookingId);
        File design = ResourceUtils.getFile("classpath:jasper/ETicket.jrxml");
        JasperReport report = JasperCompileManager.compileReport(design.getAbsolutePath());

        Map<String, Object> map = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, new JRBeanCollectionDataSource(invoices));

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

}