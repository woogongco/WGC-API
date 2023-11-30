package com.wgc.wgcapi.Notification.controller;

import com.wgc.wgcapi.Common.Annotations.RequireToken;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Notification.service.PushNotificationService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/push-notification")
public class PushNotificationController {
    private final PushNotificationService pushNotificationService;

    @GetMapping
    @RequireToken
    public SseEmitter streamNotification(HttpServletRequest request) {
        return this.pushNotificationService.streamNotification(request);
    }
}
