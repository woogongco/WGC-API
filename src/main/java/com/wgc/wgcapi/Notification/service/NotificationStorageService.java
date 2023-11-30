package com.wgc.wgcapi.Notification.service;

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Notification.Entity.Notification;
import com.wgc.wgcapi.Notification.Repository.NotificationRepository;
import com.wgc.wgcapi.Post.Service.PostService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificationStorageService {
    private final NotificationRepository notifRepository;
    private final PostService postService;

    public Notification createNotificationStorage(Notification notificationStorage) {
        return notifRepository.save(notificationStorage);
    }

    public Notification getNotificationsByID(Long id) {
        return notifRepository.findById(id).orElseThrow(() -> new RuntimeException("notification not found!"));
    }

    public List<Notification> getNotificationsByMemberIdNotRead(Long userID) {
        return notifRepository.findByMemberToIdAndDeliveredFalse(userID);
    }

    public ResponseDto getNotificationsByMemberId(HttpServletRequest request) {
        Member member = postService.getMemberInfo(request);
        List<Notification> notifications = notifRepository.findByMemberToId(member.getId());
        return new ResponseDto(HttpStatus.OK, notifications);
    }

    public ResponseDto changeNotifStatusToRead(Long notificationID) {
        Notification notify = notifRepository.findById(notificationID)
                .orElseThrow(() -> new RuntimeException("Notification not found!"));
        notify.setRead(true);
        Notification updatedNotification = notifRepository.save(notify);
        return new ResponseDto(HttpStatus.OK, updatedNotification);
    }

    public void clear() {
        notifRepository.deleteAll();
    }
}
