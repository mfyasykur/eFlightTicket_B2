package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.entity.Notification;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.NotificationRepository;
import org.binar.eflightticket_b2.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Logger log =  LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> getAllNotification(Long id) {
        return notificationRepository.findAllByUsersId(id).orElseThrow(
                () -> {
                    log.error("can't find id : {}", id);
                    ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException("user", "id", id);
                    resourceNotFoundException.setApiResponse();
                    throw resourceNotFoundException;
                }
        );
    }

    @Override
    public Notification addNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

}
