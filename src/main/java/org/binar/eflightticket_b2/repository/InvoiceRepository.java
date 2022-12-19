package org.binar.eflightticket_b2.repository;

import org.binar.eflightticket_b2.entity.Invoice;
import java.util.List;

public interface InvoiceRepository {
    List<Invoice> get(Long bookingId);
}