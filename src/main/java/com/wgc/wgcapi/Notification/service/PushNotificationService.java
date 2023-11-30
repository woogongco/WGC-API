package com.wgc.wgcapi.Notification.service;

import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Notification.Entity.Notification;
import com.wgc.wgcapi.Notification.Repository.NotificationRepository;
import com.wgc.wgcapi.Post.Service.PostService;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PushNotificationService {
    private final PostService postService;
    private final NotificationRepository notificationStorageRepository;
    private final TaskScheduler taskScheduler;

    public SseEmitter streamNotification(HttpServletRequest request) {
        final SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        Member member = postService.getMemberInfo(request);
        taskScheduler.scheduleAtFixedRate(() -> sendNotification(emitter, member), 1000);
        emitter.onCompletion(() -> log.info("Emitter for user {} completed", member));
        emitter.onTimeout(() -> log.info("Emitter for user {} timed out", member));
        emitter.onError(e -> log.error("Emitter for user {} encountered error", member, e));
        return emitter;

    }

    private List<Notification> getNewNotifications(Member member) {
        List<Notification> notifications = notificationStorageRepository.findByMemberToAndDeliveredFalse(member);
        notifications.forEach(notification -> notification.setDelivered(true));
        notificationStorageRepository.saveAll(notifications);
        return notifications;
    }

    public void sendNotification(SseEmitter emitter, Member member) {
        List<Notification> notifications = getNewNotifications(member);
        if (!notifications.isEmpty()) {
            try {
                for (Notification notification : notifications) {
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .id(UUID.randomUUID().toString())
                            .name(notification.getNotificationType().name().toLowerCase())
                            .data(notification);
                    emitter.send(event);
                }
            } catch (IOException e) {
                log.error("Error sending notification to client for user {}", member, e);
                emitter.completeWithError(e);
            }
        }
    }
}
