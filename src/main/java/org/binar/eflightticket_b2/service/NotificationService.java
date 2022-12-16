package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.entity.Notification;
import org.binar.eflightticket_b2.entity.Users;

import java.util.List;

public interface NotificationService {

    List<Notification> getAllNotification(Long id);
    Notification addNotification(Notification notification);
}
