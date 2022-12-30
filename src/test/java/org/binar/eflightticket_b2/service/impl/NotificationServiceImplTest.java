package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {
    @Mock
    NotificationRepository notificationRepository;

    @InjectMocks
    NotificationServiceImpl notificationService;

    @Test
    void getAllNotification() {
    }

    @Test
    void addNotification() {
    }
}