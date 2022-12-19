package org.binar.eflightticket_b2.repository;

import org.binar.eflightticket_b2.entity.Invoice;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Invoice> get(Long bookingId) {
        String sql =
                "SELECT bk.id, ps.id, sd.departureDate, sd.arrivalDate, sd.departureTime, sd.arrivalTime, ci.cityName, co.countryName, ai.airportName, ai.airportCode, ps.gender, ps.firstName, ps.lastName, ps.age, ps.ageCategory " +
                "FROM Booking as bk JOIN Passenger as ps ON bk.id = ps.booking_id, Schedule as sd, Country as co, City as ci, Airport as ai " +
                "WHERE bk.id = :id";

        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.setParameter("id", bookingId);

        List<Object[]> invoices =  (List<Object[]>) nativeQuery.getResultList();

        List<Invoice> invoiceList = new ArrayList<>();
        for (int i = 0; i < invoices.size(); i++) {
            Invoice invoice = new Invoice();

            invoice.setBookingId((Long) invoices.get(i)[0]);
            invoice.setPassengerId((Long) invoices.get(i)[1]);
            invoice.setDepartureDate((Date) invoices.get(i)[2]);
            invoice.setArrivalDate((Date) invoices.get(i)[3]);
            invoice.setDepartureTime((Time) invoices.get(i)[4]);
            invoice.setArrivalTime((Time) invoices.get(i)[5]);
            invoice.setCityName((String) invoices.get(i)[6]);
            invoice.setCountryName((String) invoices.get(i)[7]);
            invoice.setAirportName((String) invoices.get(i)[8]);
            invoice.setGender((Enum) invoices.get(i)[9]);
            invoice.setFirstName((String) invoices.get(i)[10]);
            invoice.setLastName((String) invoices.get(i)[11]);
            invoice.setAge((Long) invoices.get(i)[12]);
            invoice.setAgeCategory((Enum) invoices.get(i)[13]);

            invoiceList.add(invoice);
        }
        return invoiceList;
    }
}
