package ru.itgroup.intouch.service.creators;

import lombok.RequiredArgsConstructor;
import org.jooq.TableField;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.contracts.service.creators.NotificationCreator;
import ru.itgroup.intouch.tables.records.NotificationSettingsRecord;

import java.util.Locale;

import static ru.itgroup.intouch.Tables.NOTIFICATION_SETTINGS;

@Service
@RequiredArgsConstructor
public class FriendRequestNotificationCreator extends AbstractNotificationCreator implements NotificationCreator {
    private static final TableField<NotificationSettingsRecord, Boolean> TABLE_FIELD =
            NOTIFICATION_SETTINGS.FRIEND_REQUEST;

    private final MessageSource messageSource;

    @Override
    public TableField<NotificationSettingsRecord, Boolean> getTableField() {
        return TABLE_FIELD;
    }

    public String getContent(Long entityId) {
        return messageSource.getMessage("notification.friend_request", null, Locale.getDefault());
    }
}
