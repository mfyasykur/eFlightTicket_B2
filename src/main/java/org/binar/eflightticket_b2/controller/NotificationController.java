package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.entity.Notification;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notification/getall")
    public ResponseEntity<ApiResponse> getAllNotificationByUser(@Valid @RequestParam("id") Long id){
        List<Notification> allNotification = notificationService.getAllNotification(id);
        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Success", allNotification);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
