package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.entity.Notification;
import org.binar.eflightticket_b2.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {
    @Mock
    NotificationRepository notificationRepository;

    @InjectMocks
    NotificationServiceImpl notificationService;

    @Test
    void getAllNotificationSuccess() {
        Notification notification = new Notification();
        notification.setId(1l);
        Notification notification2 = new Notification();
        notification.setId(2l);
        List<Notification> listOfNotification = List.of(notification, notification2);
        when(notificationRepository.findAllByUsersId(anyLong())).thenReturn(Optional.of(listOfNotification));
        notificationService.getAllNotification(anyLong());
    }

    @Test
    void addNotification() {
    }
}