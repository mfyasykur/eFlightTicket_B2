package org.binar.eflightticket_b2.service.impl;

import org.assertj.core.api.Assertions;
import org.binar.eflightticket_b2.entity.Notification;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {
    @Mock
    NotificationRepository notificationRepository;

    @InjectMocks
    NotificationServiceImpl notificationService;

    @Test
    void getAllNotificationSuccess() {
        Notification notification = new Notification();
        notification.setId(1L);
        Notification notification2 = new Notification();
        notification.setId(2L);
        List<Notification> listOfNotification = List.of(notification, notification2);
        when(notificationRepository.findAllByUsersId(anyLong())).thenReturn(Optional.of(listOfNotification));
        notificationService.getAllNotification(anyLong());
    }

    @Test
    void getAllNotificationFailed() {
        when(notificationRepository.findAllByUsersId(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(()->  notificationService.getAllNotification(1L))
                .isInstanceOf(ResourceNotFoundException.class);
       ;
    }

    @Test
    void addNotification() {
        Notification notification = new Notification();
        notification.setId(1L);
        when(notificationRepository.save(notification)).thenReturn(notification);
        notificationService.addNotification(notification);
    }
}