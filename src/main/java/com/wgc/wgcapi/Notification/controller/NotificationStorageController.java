package com.wgc.wgcapi.Notification.controller;

import com.wgc.wgcapi.Common.Annotations.RequireToken;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Notification.Entity.Notification;
import com.wgc.wgcapi.Notification.service.NotificationStorageService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationStorageController {
    private final NotificationStorageService notifService;

    @GetMapping
    @RequireToken
    public ResponseDto getNotificationsByMemberId(HttpServletRequest request) {
        return notifService.getNotificationsByMemberId(request);
    }

    @PatchMapping("/read/{notificationId}")
    public ResponseDto changeNotifStatusToRead(@PathVariable Long notificationId) {
        return notifService.changeNotifStatusToRead(notificationId);
    }
}
