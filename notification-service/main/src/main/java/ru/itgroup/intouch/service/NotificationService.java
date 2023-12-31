package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.Notification;
import model.account.Account;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.annotation.Loggable;
import ru.itgroup.intouch.dto.response.CountDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationCountDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationListDto;
import ru.itgroup.intouch.mapper.NotificationListMapper;
import ru.itgroup.intouch.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationListMapper notificationListMapper;
    private final UserService userService;

    @Loggable
    public NotificationListDto getNotifications(Long userId) {
        Account receiver = userService.getUser(userId);
        if (receiver == null) {
            return notificationListMapper.getDestination(new ArrayList<>());
        }

        List<Notification> notifications = notificationRepository.findByReceiverOrderByCreatedAtDesc(receiver);
        readNotifications(notifications);

        return notificationListMapper.getDestination(notifications);
    }

    @Loggable
    public NotificationCountDto countNewNotifications(Long userId) {
        Account receiver = userService.getUser(userId);
        long notificationsCount = notificationRepository.countByReceiverAndReadAtIsNull(receiver);

        return NotificationCountDto.builder().data(new CountDto(notificationsCount)).build();
    }

    private void readNotifications(@NotNull List<Notification> notifications) {
        if (notifications.isEmpty()) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        notifications.forEach(notification -> notification.setReadAt(now));
        notificationRepository.saveAll(notifications);
    }
}
