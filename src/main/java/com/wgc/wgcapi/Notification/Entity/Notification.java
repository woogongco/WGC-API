package com.wgc.wgcapi.Notification.Entity;

import com.wgc.wgcapi.Member.Entity.Member;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "notification", catalog = "wgc")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"memberFrom", "memberTo"})
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_from_id")
    private Member memberFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_to_id")
    private Member memberTo;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
    private boolean delivered;
    private boolean read;

    public Notification(String content, NotificationType notificationType, Member memberFrom, Member memberTo) {
        this.content = content;
        this.notificationType = notificationType;
        this.memberFrom = memberFrom;
        this.memberTo = memberTo;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
