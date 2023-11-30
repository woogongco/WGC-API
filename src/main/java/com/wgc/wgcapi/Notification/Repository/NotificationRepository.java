package com.wgc.wgcapi.Notification.Repository;

import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Notification.Entity.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByMemberToAndDeliveredFalse(Member memberTo);

    List<Notification> findByMemberToIdAndDeliveredFalse(Long memberId);

    List<Notification> findByMemberToId(Long memberId);
}
