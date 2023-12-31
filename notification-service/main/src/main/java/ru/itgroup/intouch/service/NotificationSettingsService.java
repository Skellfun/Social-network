package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.NotificationSettings;
import model.account.Account;
import model.enums.NotificationType;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.annotation.Loggable;
import ru.itgroup.intouch.dto.request.NotificationSettingsDto;
import ru.itgroup.intouch.dto.response.settings.SettingsItemDto;
import ru.itgroup.intouch.mapper.NotificationSettingsMapper;
import ru.itgroup.intouch.repository.NotificationSettingsRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationSettingsService {
    private final NotificationSettingsRepository notificationSettingsRepository;
    private final NotificationSettingsMapper notificationSettingsMapper;
    private final UserService userService;

    @Loggable
    public List<SettingsItemDto> getSettings(Long userId)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        NotificationSettings notificationSettings = getNotificationSettingsModel(userId);
        return notificationSettingsMapper.getDestination(notificationSettings);
    }

    @Loggable
    public void updateSettings(@NotNull NotificationSettingsDto notificationSettingsDto, Long userId) {
        NotificationSettings notificationSettings = getNotificationSettingsModel(userId);
        NotificationType notificationType = NotificationType.valueOf(notificationSettingsDto.getNotificationType());
        switch (notificationType) {
            case POST -> notificationSettings.setPost(notificationSettingsDto.isEnable());
            case POST_COMMENT -> notificationSettings.setPostComment(notificationSettingsDto.isEnable());
            case COMMENT_COMMENT -> notificationSettings.setCommentComment(notificationSettingsDto.isEnable());
            case MESSAGE -> notificationSettings.setMessage(notificationSettingsDto.isEnable());
            case FRIEND_REQUEST -> notificationSettings.setFriendRequest(notificationSettingsDto.isEnable());
            case FRIEND_BIRTHDAY -> notificationSettings.setFriendBirthday(notificationSettingsDto.isEnable());
            case SEND_EMAIL_MESSAGE -> notificationSettings.setSendEmailMessage(notificationSettingsDto.isEnable());
        }

        notificationSettingsRepository.save(notificationSettings);
    }

    private @NotNull NotificationSettings getNotificationSettingsModel(Long userId) {
        Account account = userService.getUser(userId);
        NotificationSettings notificationSettings = notificationSettingsRepository.findByUser(account);
        if (notificationSettings == null) {
            notificationSettings = new NotificationSettings();
            notificationSettings.setUser(account);
            notificationSettingsRepository.save(notificationSettings);
        }

        return notificationSettings;
    }
}
